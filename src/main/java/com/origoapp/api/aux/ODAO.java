package com.origoapp.api.aux;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.googlecode.objectify.Key;

import com.origoapp.api.auth.OAuthMeta;
import com.origoapp.api.model.OReplicatedEntity;
import com.origoapp.api.model.OMember;
import com.origoapp.api.model.OMemberResidency;
import com.origoapp.api.model.OMembership;
import com.origoapp.api.model.OOrigo;
import com.origoapp.api.model.OReplicatedEntityRef;

import static com.origoapp.api.aux.OObjectifyService.ofy;


public class ODAO
{
    public OMeta m;

    
    public ODAO(OMeta meta)
    {
        super();
        
        m = meta;
    }
    
    
    public void putAuthToken(String authToken)
    {
        OMemberProxy memberProxy = m.getMemberProxy();
        Collection<OAuthMeta> authMetaItems = ofy().load().keys(memberProxy.authMetaKeys).values();
        
        if (authMetaItems.size() > 0) {
            for (OAuthMeta authMeta : authMetaItems) {
                if (authMeta.deviceId.equals(m.getDeviceId())) {
                    memberProxy.authMetaKeys.remove(Key.create(OAuthMeta.class, authMeta.authToken));
                    ofy().delete().entity(authMeta);
                    
                    OLog.log().fine(m.meta() + String.format("Deleted old auth token (token: %s; user: %s).", authMeta.authToken, m.getEmail()));
                }
            }
        } else {
            memberProxy.didSignUp = true;
        }
        
        OAuthMeta authMeta = new OAuthMeta(authToken, m.getEmail(), m.getDeviceId(), m.getDeviceType());
        memberProxy.authMetaKeys.add(Key.create(OAuthMeta.class, authToken));
        
        ofy().save().entities(authMeta, memberProxy).now();
        
        OLog.log().fine(m.meta() + String.format("Persisted new auth token (token: %s; user: %s).", authToken, m.getEmail()));
    }
    
    
    public List<OReplicatedEntity> fetchEntities(Date deviceReplicationDate)
    {
        OLog.log().fine(m.meta() + "Fetching entities modified since: " + ((deviceReplicationDate != null) ? deviceReplicationDate.toString() : "<dawn of time>"));
        
        OMemberProxy memberProxy = m.getMemberProxy();
        Collection<OMembership> memberships = ofy().load().keys(memberProxy.membershipKeys).values();
        
        Set<Key<OReplicatedEntity>> additionalEntityKeys = new HashSet<Key<OReplicatedEntity>>();
        Set<OReplicatedEntity> fetchedEntities = new HashSet<OReplicatedEntity>();
        
        boolean memberProxyNeedsSaving = false;
        
        for (OMembership membership : memberships) {
            if ((membership.isActive || membership.getClass().equals(OMemberResidency.class)) && !membership.isGhost) {
                List<OReplicatedEntity> membershipEntities = null;
                
                if (deviceReplicationDate != null) {
                    membershipEntities = ofy().load().type(OReplicatedEntity.class).ancestor(membership.origoKey).filter("dateReplicated >", deviceReplicationDate).list();
                } else {
                    membershipEntities = ofy().load().type(OReplicatedEntity.class).ancestor(membership.origoKey).list();
                }
                
                for (OReplicatedEntity entity : membershipEntities) {
                    if (entity.getClass().equals(OReplicatedEntityRef.class)) {
                        additionalEntityKeys.add(((OReplicatedEntityRef)entity).referencedEntityKey);
                    }
                    
                    fetchedEntities.add(entity);
                }
            } else {
                fetchedEntities.add(membership);
                
                if (membership.isGhost) {
                    memberProxy.membershipKeys.remove(Key.create(membership.origoKey, OMembership.class, membership.entityId));
                    memberProxyNeedsSaving = true;
                } else {
                    additionalEntityKeys.add(Key.create(membership.origoKey, OReplicatedEntity.class, membership.origoId));
                }
            }
        }
        
        fetchedEntities.addAll(ofy().load().keys(additionalEntityKeys).values());
        
        if (memberProxyNeedsSaving) {
            ofy().save().entity(memberProxy);
        }
        
        if (m.isAuthenticating()) {
            for (OReplicatedEntity entity : fetchedEntities) {
                if (entity.getClass().equals(OMember.class)) {
                    m.validateIfUser((OMember)entity);
                }
            }
        }
        
        OLog.log().fine(m.meta() + "Fetched entities: " + fetchedEntities.toString());
        
        return new ArrayList<OReplicatedEntity>(fetchedEntities);
    }
    
    
    public void replicateEntities(List<OReplicatedEntity> entityList)
    {
        new OEntityReplicator().replicate(entityList);
    }
    
    
    private class OEntityReplicator
    {
        private Date dateReplicated = new Date();
        
        private Set<OReplicatedEntity> entitiesToReplicate;
        private Set<Key<OReplicatedEntity>> entityKeysForDeletion;
        
        private Map<String, Set<Key<OMembership>>> addedMembershipKeysByProxyId;
        private Map<String, Set<Key<OMembership>>> revokedMembershipKeysByProxyId;
        
        private Map<String, OMember> modifiedMembersByMemberId;
        private Map<String, OMember> modifiedMembersByEmail;
        
        private Map<Key<OMemberProxy>, OMemberProxy> affectedMemberProxiesByKey;
        private Set<Key<OMemberProxy>> affectedMemberProxyKeys;
        private Set<OMemberProxy> driftingMemberProxies;
        
        
        private void processEntityGhost(OReplicatedEntity entityGhost)
        {
            if (OMembership.class.isAssignableFrom(entityGhost.getClass())) {
                entitiesToReplicate.add(entityGhost);
                
                OMembership revokedMembership = (OMembership)entityGhost;
                String memberRefId = revokedMembership.member.entityId + "#" + revokedMembership.origoId;
                entityKeysForDeletion.add(Key.create(revokedMembership.origoKey, OReplicatedEntity.class, memberRefId));
                
                String revokedMembershipProxyId = (revokedMembership.member.email != null) ? revokedMembership.member.email : revokedMembership.member.entityId; 
                Set<Key<OMembership>> revokedMembershipKeysForMember = revokedMembershipKeysByProxyId.get(revokedMembershipProxyId);
                
                if (revokedMembershipKeysForMember == null) {
                    revokedMembershipKeysForMember = new HashSet<Key<OMembership>>();
                    revokedMembershipKeysByProxyId.put(revokedMembershipProxyId, revokedMembershipKeysForMember);
                }
                
                revokedMembershipKeysForMember.add(Key.create(revokedMembership.origoKey, OMembership.class, revokedMembership.entityId));
            }
        }
        
        
        private void processMemberEntity(OMember member)
        {
            String proxyId = (member.email != null) ? member.email : member.entityId;
            Key<OMemberProxy> proxyKey = Key.create(OMemberProxy.class, proxyId);
            
            if (proxyId.equals(m.getEmail())) {
                affectedMemberProxiesByKey.put(proxyKey, m.getMemberProxy());
            } else {
                if (member.dateReplicated == null) {
                    affectedMemberProxiesByKey.put(proxyKey, new OMemberProxy(proxyId));
                } else {
                    affectedMemberProxyKeys.add(proxyKey);
                    modifiedMembersByMemberId.put(member.entityId, member);
                    
                    if (member.email != null) {
                        modifiedMembersByEmail.put(member.email, member);
                    }
                }
            }
        }
        
        
        private void processMembershipEntity(OMembership membership)
        {
            String proxyId = (membership.member.email != null) ? membership.member.email : membership.member.entityId;
            
            if (proxyId.equals(m.getEmail()) || (membership.dateReplicated == null)) {
                Set<Key<OMembership>> membershipKeysToAddForMember = addedMembershipKeysByProxyId.get(proxyId);
                
                if (membershipKeysToAddForMember == null) {
                    membershipKeysToAddForMember = new HashSet<Key<OMembership>>();
                    addedMembershipKeysByProxyId.put(proxyId, membershipKeysToAddForMember);
                }
                
                membershipKeysToAddForMember.add(Key.create(membership.origoKey, OMembership.class, membership.entityId));
            }
        }
        
        
        private void fetchAdditionalAffectedMemberProxies()
        {
            Set<Key<OMemberProxy>> affectedMemberProxyKeys = new HashSet<Key<OMemberProxy>>();
            
            for (String proxyId : addedMembershipKeysByProxyId.keySet()) {
                Key<OMemberProxy> proxyKey = Key.create(OMemberProxy.class, proxyId);
                
                if (!affectedMemberProxiesByKey.keySet().contains(proxyKey)) {
                    affectedMemberProxyKeys.add(proxyKey);
                }
            }
            
            for (String proxyId : revokedMembershipKeysByProxyId.keySet()) {
                affectedMemberProxyKeys.add(Key.create(OMemberProxy.class, proxyId));
            }

            if (affectedMemberProxyKeys.size() > 0) {
                affectedMemberProxiesByKey.putAll(ofy().load().keys(affectedMemberProxyKeys));
            }
        }
        
        
        private void reanchorDriftingMemberProxies()
        {
            Set<String> anchoredProxyIds = new HashSet<String>();
            
            for (OMemberProxy memberProxy : affectedMemberProxiesByKey.values()) {
                anchoredProxyIds.add(memberProxy.proxyId);
            }
            
            Set<Key<OMember>> driftingMemberKeys = new HashSet<Key<OMember>>();
            
            for (String email : modifiedMembersByEmail.keySet()) {
                if (!anchoredProxyIds.contains(email)) {
                    String memberId = modifiedMembersByEmail.get(email).entityId;
                    driftingMemberKeys.add(Key.create(Key.create(OOrigo.class, "~" + memberId), OMember.class, memberId));
                }
            }
            
            if (driftingMemberKeys.size() > 0) {
                Set<Key<OMemberProxy>> driftingMemberProxyKeys = new HashSet<Key<OMemberProxy>>();
                Set<OAuthMeta> reanchoredAuthMetaItems = new HashSet<OAuthMeta>();
                
                Collection<OMember> driftingMembers = ofy().load().keys(driftingMemberKeys).values();
                
                for (OMember member : driftingMembers) {
                    String driftingProxyId = (member.email != null) ? member.email : member.entityId;
                    driftingMemberProxyKeys.add(Key.create(OMemberProxy.class, driftingProxyId));
                }
                
                Map<String, OMemberProxy> driftingMemberProxiesByProxyId = new HashMap<String, OMemberProxy>();
                
                for (OMemberProxy memberProxy : ofy().load().keys(driftingMemberProxyKeys).values()) {
                    driftingMemberProxiesByProxyId.put(memberProxy.proxyId, memberProxy);
                }
                
                for (OMember member : driftingMembers) {
                    String driftingProxyId = (member.email != null) ? member.email : member.entityId;
                    String modifiedEmail = modifiedMembersByMemberId.get(member.entityId).email; 
                    
                    OMemberProxy driftingMemberProxy = driftingMemberProxiesByProxyId.get(driftingProxyId);
                    OMemberProxy reanchoredMemberProxy = new OMemberProxy(modifiedEmail, driftingMemberProxy);
                    
                    for (OAuthMeta authMetaItem : ofy().load().keys(reanchoredMemberProxy.authMetaKeys).values()) {
                        authMetaItem.email = modifiedEmail;
                        reanchoredAuthMetaItems.add(authMetaItem);
                    }
                    
                    driftingMemberProxies.add(driftingMemberProxy);
                    affectedMemberProxiesByKey.put(Key.create(OMemberProxy.class, modifiedEmail), reanchoredMemberProxy);
                    
                    if (driftingProxyId.equals(m.getEmail())) {
                        m.setMemberProxy(reanchoredMemberProxy);
                    }
                }
                
                if (reanchoredAuthMetaItems.size() > 0) {
                    ofy().save().entities(reanchoredAuthMetaItems).now();
                }
            }
        }
        
        
        private void updateAffectedEntityReferences()
        {
            Set<Key<OReplicatedEntityRef>> affectedEntityRefKeys = new HashSet<Key<OReplicatedEntityRef>>();
            
            for (OMember member : modifiedMembersByMemberId.values()) {
                String proxyId = (member.email != null) ? member.email : member.entityId;
                OMemberProxy memberProxy = affectedMemberProxiesByKey.get(Key.create(OMemberProxy.class, proxyId));
                
                for (Key<OMembership> membershipKey : memberProxy.membershipKeys) {
                    Key<OOrigo> origoKey = membershipKey.getParent();
                    String origoId = origoKey.getRaw().getName();
                    String entityRefId = member.entityId + "#" + origoId;
                    
                    affectedEntityRefKeys.add(Key.create(origoKey, OReplicatedEntityRef.class, entityRefId));
                }
            }
            
            Collection<OReplicatedEntityRef> affectedEntityRefs = ofy().load().keys(affectedEntityRefKeys).values();
            
            for (OReplicatedEntityRef entityRef : affectedEntityRefs) {
                entityRef.dateReplicated = dateReplicated;
            }
            
            entitiesToReplicate.addAll(affectedEntityRefs);
        }
        
        
        private void updateAffectedRelationshipKeys()
        {
            for (OMemberProxy memberProxy : affectedMemberProxiesByKey.values()) {
                Set<Key<OMembership>> addedMembershipKeysForMember = addedMembershipKeysByProxyId.get(memberProxy.proxyId);
                Set<Key<OMembership>> revokedMembershipKeysForMember = revokedMembershipKeysByProxyId.get(memberProxy.proxyId);
                
                if (addedMembershipKeysForMember != null) {
                    memberProxy.membershipKeys.addAll(addedMembershipKeysForMember);
                }
                
                if (!memberProxy.didSignUp && (revokedMembershipKeysForMember != null)) {
                    memberProxy.membershipKeys.removeAll(revokedMembershipKeysForMember);
                    
                    if (memberProxy.membershipKeys.size() == 1) {
                        Key<OMembership> rootMembershipKey = memberProxy.membershipKeys.iterator().next();
                        String rootOrigoId = rootMembershipKey.getParent().getName();
                        Key<OOrigo> rootOrigoKey = Key.create(OOrigo.class, rootOrigoId);
                        
                        entityKeysForDeletion.add(Key.create(rootOrigoKey, OReplicatedEntity.class, rootMembershipKey.getName()));
                        entityKeysForDeletion.add(Key.create(rootOrigoKey, OReplicatedEntity.class, rootOrigoId));
                        entityKeysForDeletion.add(Key.create(rootOrigoKey, OReplicatedEntity.class, rootOrigoId.substring(1)));
                        
                        driftingMemberProxies.add(memberProxy);
                    }
                }
            }
        }
        
        
        public OEntityReplicator()
        {
            entitiesToReplicate = new HashSet<OReplicatedEntity>();
            entityKeysForDeletion = new HashSet<Key<OReplicatedEntity>>();
            
            addedMembershipKeysByProxyId = new HashMap<String, Set<Key<OMembership>>>();
            revokedMembershipKeysByProxyId = new HashMap<String, Set<Key<OMembership>>>();
            
            modifiedMembersByMemberId = new HashMap<String, OMember>();
            modifiedMembersByEmail = new HashMap<String, OMember>();
            
            affectedMemberProxiesByKey = new HashMap<Key<OMemberProxy>, OMemberProxy>();
            affectedMemberProxyKeys = new HashSet<Key<OMemberProxy>>();
            driftingMemberProxies = new HashSet<OMemberProxy>();
        }
        
        
        public void replicate(List<OReplicatedEntity> entityList)
        {
            for (OReplicatedEntity entity : entityList) {
                entity.origoKey = Key.create(OOrigo.class, entity.origoId);
                
                if (entity.isGhost) {
                    processEntityGhost(entity);
                } else {
                    entitiesToReplicate.add(entity);
                    
                    if (entity.getClass().equals(OMember.class)) {
                        processMemberEntity((OMember)entity);
                    } else if (OMembership.class.isAssignableFrom(entity.getClass())) {
                        processMembershipEntity((OMembership)entity);
                    }
                }
                
                entity.dateReplicated = dateReplicated;
            }
            
            fetchAdditionalAffectedMemberProxies();
            reanchorDriftingMemberProxies();
            updateAffectedEntityReferences();
            updateAffectedRelationshipKeys();
            
            if (affectedMemberProxiesByKey.size() > 0) {
                ofy().save().entities(affectedMemberProxiesByKey.values()).now();
            }
            
            if (driftingMemberProxies.size() > 0) {
                ofy().delete().entities(driftingMemberProxies);
            }
            
            ofy().save().entities(entitiesToReplicate).now();
            OLog.log().fine(m.meta() + "Replicated entities: " + entitiesToReplicate.toString());

            if (entityKeysForDeletion.size() > 0) {
                ofy().delete().keys(entityKeysForDeletion);
                OLog.log().fine(m.meta() + "Permanently deleted entities: " + entityKeysForDeletion);
            }
        }
    }
}

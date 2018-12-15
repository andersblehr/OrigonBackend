package co.origon.api.entities;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
@Cache(expirationSeconds = 600)
public class OAuthInfo
{
    public @Id String email;
    
    public String deviceId;
    public String passwordHash;
    public String activationCode;
    
    
    public OAuthInfo() {}
    
    
    public OAuthInfo(String email, String deviceId, String passwordHash, String activationCode)
    {
        this.email = email;
        this.deviceId = deviceId;
        this.passwordHash = passwordHash;
        this.activationCode = activationCode;
    }
}
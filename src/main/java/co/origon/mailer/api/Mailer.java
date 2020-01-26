package co.origon.mailer.api;

import co.origon.api.model.ofy.entity.OMember;
import co.origon.api.model.ofy.entity.OMemberProxy;
import co.origon.api.model.ofy.entity.OMembership;
import co.origon.api.model.ofy.entity.OOrigo;

public interface Mailer {
  Mailer language(String languageCode);

  void sendRegistrationEmail(String email, String activationCode);

  void sendPasswordResetEmail(String email, String temporaryPassword);

  void sendEmailActivationCode(String email, String activationCode);

  // TODO: Need SOLIDification
  void sendInvitation(String email, OMemberProxy userProxy);

  void sendInvitation(
      OMemberProxy userProxy, OMembership invitationMembership, OOrigo invitationOrigo);

  void sendEmailChangeNotification(OMember member, String driftingEmail, OMemberProxy userProxy);

  enum Language {
    ENGLISH("en"),
    GERMAN("de"),
    NORWEGIAN("nb");

    private final String languageCoode;

    Language(String languageCoode) {
      this.languageCoode = languageCoode;
    }

    public static Language fromCode(String languageCoode) {
      for (Language language : Language.values()) {
        if (language.languageCoode.equals(languageCoode)) {
          return language;
        }
      }

      throw new IllegalArgumentException("Unknown or unsupported language: " + languageCoode);
    }
  }
}

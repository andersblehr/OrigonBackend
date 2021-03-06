package co.origon.api.model.server.ofy;

import co.origon.api.model.server.DeviceCredentials;
import co.origon.api.repository.ofy.OfyMapper;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.util.Date;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class OAuthMeta implements OfyMapper<DeviceCredentials> {

  @Id private String authToken;
  private String email;
  private String deviceId;
  private String deviceType;
  private Date dateExpires;

  public String deviceToken() {
    return authToken;
  }

  public String email() {
    return email;
  }

  public String deviceId() {
    return deviceId;
  }

  public String deviceType() {
    return deviceType;
  }

  public Date expiresAt() {
    return dateExpires;
  }

  public OAuthMeta(DeviceCredentials deviceCredentials) {
    authToken = deviceCredentials.authToken();
    email = deviceCredentials.email();
    deviceId = deviceCredentials.deviceId();
    deviceType = deviceCredentials.deviceType();
    dateExpires = deviceCredentials.dateExpires();
  }

  @Override
  public DeviceCredentials fromOfy() {
    return DeviceCredentials.builder()
        .authToken(authToken)
        .email(email)
        .deviceId(deviceId)
        .deviceType(deviceType)
        .dateExpires(dateExpires)
        .build();
  }
}

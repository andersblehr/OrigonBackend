package co.origon.api.model.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@Getter()
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(
    value = {"entityKey", "parentKey", "proxyKey", "proxyId", "expired", "minor"},
    ignoreUnknown = true)
public class Member extends ReplicatedEntity {

  private final String name;
  private final String gender;
  private final Date dateOfBirth;
  private final String mobilePhone;
  private final String email;
  private final String motherId;
  private final String fatherId;
  private final boolean isMinor;
  private final String createdIn;
  private final Date activeSince;
  private final String settings;

  @Override
  @JsonIgnore
  public boolean isMember() {
    return true;
  }

  @JsonIgnore
  public String proxyId() {
    return hasEmail() ? email : entityId();
  }

  @JsonIgnore
  public boolean hasEmail() {
    return email() != null && email().length() > 0;
  }
}

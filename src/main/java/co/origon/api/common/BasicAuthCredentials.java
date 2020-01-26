package co.origon.api.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class BasicAuthCredentials {
  private static ThreadLocal<BasicAuthCredentials> localCredentials;

  // private static final String DEFAULT_SALT = "RKPAAXYFRYDVM3";
  private static final String DEFAULT_SALT = "socroilgao";
  private static final int MIN_PASSWORD_LENGTH = 6;

  private final String email;
  private final String password;
  private final String passwordHash;

  public static BasicAuthCredentials validate(String authorizationHeader) {
    if (localCredentials != null)
      throw new RuntimeException("Basic auth credentials have already been validated");

    final BasicAuthCredentials credentials = new BasicAuthCredentials(authorizationHeader);
    localCredentials = ThreadLocal.withInitial(() -> credentials);
    return localCredentials.get();
  }

  public static BasicAuthCredentials getCredentials() {
    if (localCredentials == null)
      throw new RuntimeException("No basic auth credentials have been validated, cannot get");
    return localCredentials.get();
  }

  public static boolean hasCredentials() {
    return localCredentials != null;
  }

  public static void dispose() {
    localCredentials = null;
  }

  static String hashUsingSHA1(String string) {
    String output = "";

    try {
      byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

      MessageDigest digest = MessageDigest.getInstance("SHA-1");
      byte[] digestOutput = digest.digest(bytes);

      for (int i = 0; i < digest.getDigestLength(); i++) {
        output = output.concat(String.format("%02x", digestOutput[i]));
      }
    } catch (Exception e) {
      throw new WebApplicationException(e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return output;
  }

  static String generatePasswordHash(String password) {
    String passwordHash = null;

    if (password != null) {
      passwordHash = hashUsingSHA1(seasonedString(password));
    }

    return passwordHash;
  }

  static String seasonedString(String string) {
    String stringHash = hashUsingSHA1(string);
    String seasoningHash = hashUsingSHA1(DEFAULT_SALT);

    int hashLength = stringHash.length();

    byte[] stringBytes = stringHash.getBytes();
    byte[] seasoningBytes = seasoningHash.getBytes();
    byte[] seasonedBytes = new byte[hashLength];

    for (int i = 0; i < hashLength; i++) {
      byte char1 = stringBytes[i];
      byte char2 = seasoningBytes[hashLength - (i + 1)];

      if (char1 >= char2) {
        seasonedBytes[i] = (byte) (char1 - char2 + 33); // ASCII 33 = '!'
      } else {
        seasonedBytes[i] = (byte) (char2 - char1 + 33);
      }
    }

    return new String(seasonedBytes);
  }

  private BasicAuthCredentials(String authorizationHeader) {
    if (authorizationHeader == null || authorizationHeader.length() == 0)
      throw new IllegalArgumentException("Missing Authorization header");

    final String[] authElements = authorizationHeader.split(" ");
    if (authElements.length != 2)
      throw new IllegalArgumentException("Invalid Authorization header: " + authorizationHeader);
    if (!authElements[0].equals("Basic"))
      throw new IllegalArgumentException(
          "Invalid authentication scheme for HTTP basic auth: " + authElements[0]);

    final String credentialsString;
    try {
      credentialsString =
          new String(java.util.Base64.getDecoder().decode(authElements[1].getBytes()));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("DeviceCredentials are not base 64 encoded");
    }

    final String[] credentials = credentialsString.split(":");
    if (credentials.length != 2)
      throw new IllegalArgumentException("Invalid basic auth credentials: " + credentialsString);
    if (!credentials[0].matches("^.+@.+\\..+$"))
      throw new IllegalArgumentException("Invalid email address: " + credentials[0]);
    if (credentials[1].length() < MIN_PASSWORD_LENGTH)
      throw new IllegalArgumentException("Password is too short: " + credentials[1]);

    email = credentials[0];
    password = credentials[1];
    passwordHash = generatePasswordHash(password);
  }
}

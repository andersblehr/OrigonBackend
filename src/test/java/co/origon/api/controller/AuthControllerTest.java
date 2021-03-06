package co.origon.api.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.origon.api.common.BasicAuthCredentials;
import co.origon.api.common.Mailer;
import co.origon.api.common.Mailer.Language;
import co.origon.api.common.UrlParams;
import co.origon.api.model.client.ReplicatedEntity;
import co.origon.api.model.server.DeviceCredentials;
import co.origon.api.model.server.MemberProxy;
import co.origon.api.model.server.OneTimeCredentials;
import co.origon.api.repository.Repository;
import co.origon.api.service.AuthService;
import co.origon.api.service.ReplicationService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

  private static final String USER_ID = "3eca0277-b8aa-4293-a028-44e0746c3082";
  private static final String USER_EMAIL = "user@example.com";
  private static final String USER_PASSWORD = "password";
  private static final String AUTHORIZATION_HEADER = getAuthorizationHeader();
  private static final String DEVICE_TOKEN = "96ae6cd160219b214ba8fe816344a478145a2a61";
  private static final String DEVICE_ID = "e53f352b-84c6-4b8a-8065-05b53a54c7a1";
  private static final String DEVICE_TYPE = "Device";
  private static final String LANGUAGE = "nb";

  @Mock private ContainerRequestContext requestContext;
  @Mock private Repository<MemberProxy> memberProxyRepository;
  @Mock private Repository<OneTimeCredentials> oneTimeCredentialsRepository;
  @Mock private Repository<DeviceCredentials> deviceCredentialsRepository;
  @Mock private List<ReplicatedEntity> fetchedEntities;
  @Mock private ReplicationService replicationService;
  @Mock private Mailer mailer;

  private BasicAuthCredentials credentials = new BasicAuthCredentials(AUTHORIZATION_HEADER);
  private AuthController authController;

  private final MemberProxy invitedUserProxy =
      MemberProxy.builder()
          .id(USER_EMAIL)
          .memberId(USER_ID)
          .passwordHash("some")
          .deviceToken(DEVICE_TOKEN)
          .build();
  private final MemberProxy uninvitedUserProxy =
      MemberProxy.builder().id(USER_EMAIL).passwordHash("some").deviceToken(DEVICE_TOKEN).build();
  private final OneTimeCredentials validOneTimeCredentials =
      OneTimeCredentials.builder()
          .email(USER_EMAIL)
          .deviceId(DEVICE_ID)
          .passwordHash("433ad0607f4fc7fedc58aa990107a32144327c63")
          .activationCode("some")
          .build();
  private final OneTimeCredentials invalidOneTimeCredentials =
      OneTimeCredentials.builder()
          .email(USER_EMAIL)
          .deviceId(DEVICE_ID)
          .passwordHash("invalid")
          .activationCode("some")
          .build();
  private final DeviceCredentials deviceCredentials =
      DeviceCredentials.builder()
          .email(USER_EMAIL)
          .deviceId(DEVICE_ID)
          .authToken(DEVICE_TOKEN)
          .deviceType(DEVICE_TYPE)
          .dateExpires(new Date(System.currentTimeMillis() + 1000L))
          .build();

  @BeforeEach
  void setUp() {
    authController =
        new AuthController(
            new AuthService(
                memberProxyRepository,
                oneTimeCredentialsRepository,
                deviceCredentialsRepository,
                mailer),
            replicationService,
            requestContext);
  }

  @Nested
  @DisplayName("GET /auth/register")
  class WhenRegisterUser {

    @Test
    @DisplayName("Given missing device ID, then return 400 BAD_REQUEST")
    void givenMissingDeviceID_thenReturn400BadRequest() {
      // then
      WebApplicationException e =
          assertThrows(
              BadRequestException.class,
              () ->
                  // when
                  authController.registerUser(AUTHORIZATION_HEADER, null, LANGUAGE));
      assertEquals("Missing parameter: duid", e.getMessage());
    }

    @Test
    @DisplayName("Given new user, then return one time credentials")
    void givenNewUser_thenReturnOneTimeCredentials() {
      // given
      establishCredentials();
      when(oneTimeCredentialsRepository.save(any(OneTimeCredentials.class)))
          .thenReturn(validOneTimeCredentials);
      when(mailer.using(Language.fromCode(LANGUAGE))).thenReturn(mailer);

      // when
      final Response response =
          authController.registerUser(AUTHORIZATION_HEADER, DEVICE_ID, LANGUAGE);

      // then
      verify(mailer).sendRegistrationEmail(eq(USER_EMAIL), anyString());
      assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
      assertEquals(validOneTimeCredentials, response.getEntity());
    }

    @Test
    @DisplayName("Given existing user, then return 409 CONFLICT")
    void givenExistingUser_thenReturn409Conflict() {
      // given
      establishCredentials();
      establishDidRegister(true);

      // then
      WebApplicationException e =
          assertThrows(
              WebApplicationException.class,
              () ->
                  // when
                  authController.registerUser(AUTHORIZATION_HEADER, DEVICE_ID, LANGUAGE));
      assertEquals(Status.CONFLICT.getStatusCode(), e.getResponse().getStatus());
      assertEquals("User is already registered and active", e.getMessage());
    }
  }

  @Nested
  @DisplayName("GET /auth/activate")
  class WhenActivateUser {

    @Test
    @DisplayName("Given non-invited user, then activate user and return no user entities")
    void givenNonInvitedUser_thenActivateUserAndReturnNoUserEntities() {
      // given
      establishCredentials();
      establishInvited(false);
      establishDidRegister(false);
      establishValidOneTimeCredentials(true);
      establishDeviceCredentialsCreated();
      establishActivated(false);

      // when
      Response response =
          authController.activateUser(AUTHORIZATION_HEADER, DEVICE_TOKEN, DEVICE_ID, DEVICE_TYPE);

      // then
      verify(deviceCredentialsRepository).save(any(DeviceCredentials.class));
      verify(memberProxyRepository).save(any(MemberProxy.class));
      verify(oneTimeCredentialsRepository).deleteById(USER_EMAIL);
      verify(replicationService).fetch(USER_EMAIL);

      assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
      assertNull(response.getLocation());
      assertNotNull(response.getLastModified());
    }

    @Test
    @DisplayName("Given invited user, then activate user and return user entities")
    void givenInvitedUser_thenActivateUserAndReturnUserEntities() {
      // given
      establishCredentials();
      establishInvited(true);
      establishDidRegister(false);
      establishValidOneTimeCredentials(true);
      establishDeviceCredentialsCreated();
      establishActivated(true);

      // when
      Response response =
          authController.activateUser(AUTHORIZATION_HEADER, DEVICE_TOKEN, DEVICE_ID, DEVICE_TYPE);

      // then
      verify(deviceCredentialsRepository).save(any(DeviceCredentials.class));
      verify(memberProxyRepository).save(any(MemberProxy.class));
      verify(oneTimeCredentialsRepository).deleteById(anyString());
      verify(replicationService).fetch(anyString());

      assertEquals(Status.OK.getStatusCode(), response.getStatus());
      assertNotNull(response.getLocation().toString());
      assertNotNull(response.getLastModified());
    }

    @Test
    @DisplayName("Given existing user, then return 409 CONFLICT")
    void givenExistingUser_thenReturn4009Conflict() {
      // given
      establishCredentials();
      establishDidRegister(true);

      // then
      WebApplicationException e =
          assertThrows(
              WebApplicationException.class,
              () ->
                  // when
                  authController.activateUser(
                      AUTHORIZATION_HEADER, DEVICE_TOKEN, DEVICE_ID, DEVICE_TYPE));
      assertEquals(Status.CONFLICT.getStatusCode(), e.getResponse().getStatus());
      assertEquals("User is already registered and active", e.getMessage());
    }

    @Test
    @DisplayName("Given no one time credentials, then return 400 BAD_REQUEST")
    void givenNoOneTimeCredentials_thenReturn400BadRequest() {
      // given
      establishCredentials();
      establishDidRegister(false);
      establishOneTimeCredentialsAvailable(false, false);

      // then
      Throwable e =
          assertThrows(
              BadRequestException.class,
              () ->
                  // when
                  authController.activateUser(
                      AUTHORIZATION_HEADER, DEVICE_TOKEN, DEVICE_ID, DEVICE_TYPE));
      assertEquals("User is not awaiting activation", e.getMessage());
    }

    @Test
    @DisplayName("Given mismatched credentials, then return 401 UNAUTHORIZED")
    void givenMismatchedCredentials_thenReturn401Unauthorized() {
      // given
      establishCredentials();
      establishDidRegister(false);
      establishValidOneTimeCredentials(false);

      // then
      WebApplicationException e =
          assertThrows(
              NotAuthorizedException.class,
              () ->
                  // when
                  authController.activateUser(
                      AUTHORIZATION_HEADER, DEVICE_TOKEN, DEVICE_ID, DEVICE_TYPE));
      assertEquals("Incorrect password", e.getMessage());
      final String authChallenge =
          e.getResponse().getHeaders().getFirst(HttpHeaders.WWW_AUTHENTICATE).toString();
      assertEquals("login", authChallenge);
    }

    @Test
    @DisplayName("Given missing or invalid device token, then return 400 BAD_REQUEST")
    void givenInvalidDeviceToken_thenReturn400BadRequest() {
      // given
      establishDidRegister(false);
      establishValidOneTimeCredentials(true);

      // then
      assertAll(
          "Missing or invalid device token",
          () -> {
            final Throwable eNull =
                assertThrows(
                    BadRequestException.class,
                    () ->
                        // when
                        authController.activateUser(
                            AUTHORIZATION_HEADER, null, DEVICE_ID, DEVICE_TYPE));
            assertEquals("Missing parameter: " + UrlParams.DEVICE_TOKEN, eNull.getMessage());
          },
          () -> {
            final Throwable eInvalid =
                assertThrows(
                    BadRequestException.class,
                    () ->
                        // when
                        authController.activateUser(
                            AUTHORIZATION_HEADER, "Not a token", DEVICE_ID, DEVICE_TYPE));
            assertEquals("Invalid device token format: Not a token", eInvalid.getMessage());
          });
    }

    @Test
    @DisplayName("Given missing device ID, then return 400 BAD_REQUEST")
    void givenMissingDeviceID_thenReturn400BadRequest() {
      // then
      WebApplicationException e =
          assertThrows(
              BadRequestException.class,
              () ->
                  // when
                  authController.activateUser(
                      AUTHORIZATION_HEADER, DEVICE_TOKEN, null, DEVICE_TYPE));
      assertEquals("Missing parameter: duid", e.getMessage());
    }

    @Test
    @DisplayName("Given missing device type, then return 400 BAD_REQUEST")
    void givenMissingDeviceType_thenReturn400BadRequest() {
      // then
      WebApplicationException e =
          assertThrows(
              BadRequestException.class,
              () ->
                  // when
                  authController.activateUser(AUTHORIZATION_HEADER, DEVICE_TOKEN, DEVICE_ID, null));
      assertEquals("Missing parameter: device", e.getMessage());
    }

    private void establishInvited(boolean invited) {
      lenient().when(replicationService.fetch(USER_EMAIL)).thenReturn(Optional.of(fetchedEntities));
      lenient().when(fetchedEntities.size()).thenReturn(invited ? 10 : 0);
    }

    private void establishOneTimeCredentialsAvailable(boolean available, boolean valid) {
      lenient()
          .when(oneTimeCredentialsRepository.getById(USER_EMAIL))
          .thenReturn(
              available
                  ? Optional.of(valid ? validOneTimeCredentials : invalidOneTimeCredentials)
                  : Optional.empty());
    }

    private void establishValidOneTimeCredentials(boolean valid) {
      establishOneTimeCredentialsAvailable(true, valid);
    }

    private void establishDeviceCredentialsCreated() {
      lenient()
          .when(deviceCredentialsRepository.getById(USER_EMAIL))
          .thenReturn(Optional.of(deviceCredentials));
    }

    private void establishActivated(boolean invited) {
      lenient()
          .when(memberProxyRepository.save(any(MemberProxy.class)))
          .thenReturn(invited ? invitedUserProxy : uninvitedUserProxy);
    }
  }

  private void establishCredentials() {
    when(requestContext.getProperty(BasicAuthCredentials.CONTEXT_KEY)).thenReturn(credentials);
  }

  private void establishDidRegister(boolean didRegister) {
    lenient()
        .when(memberProxyRepository.getById(USER_EMAIL))
        .thenReturn(didRegister ? Optional.of(invitedUserProxy) : Optional.empty());
  }

  private static String getAuthorizationHeader() {
    final String credentials = USER_EMAIL + ":" + USER_PASSWORD;
    return "Basic " + new String(java.util.Base64.getEncoder().encode(credentials.getBytes()));
  }
}

package usertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.user.Credentials;
import ru.yandex.user.User;
import ru.yandex.user.UserClient;
import ru.yandex.user.UserDataGenerator;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class UserDataModificationAuthorizedTests {

    private UserClient userClient;
    private User testUser;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        testUser = UserDataGenerator.createUniqueUser();
        ValidatableResponse createUserResponse = userClient.createUser(testUser);
        accessToken = createUserResponse.extract().path("accessToken");
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Изменение имени авторизованного пользователя")
    public void shouldChangeUserNameWithAuth() {
        String oldName = testUser.getName();
        testUser.setName("UpdatedName");
        ValidatableResponse updateUserResponse = userClient.updateUserWithAuth(accessToken, Credentials.from(testUser));
        int statusCode = updateUserResponse.extract().statusCode();
        boolean isUserUpdated = updateUserResponse.extract().path("success");
        assertEquals("Ожидается статус 200", SC_OK, statusCode);
        assertTrue("Ожидается успешное обновление данных пользователя", isUserUpdated);
        assertNotEquals("Ожидается изменение имени", oldName, testUser.getName());
    }

    @Test
    @DisplayName("Изменение email авторизованного пользователя")
    public void shouldChangeUserEmailWithAuth() {
        String oldEmail = testUser.getEmail();
        testUser.setEmail("updatedEmail@test.com");
        ValidatableResponse updateUserResponse = userClient.updateUserWithAuth(accessToken, Credentials.from(testUser));
        int statusCode = updateUserResponse.extract().statusCode();
        boolean isUserUpdated = updateUserResponse.extract().path("success");
        assertEquals("Ожидается статус 200", SC_OK, statusCode);
        assertTrue("Ожидается успешное обновление данных пользователя", isUserUpdated);
        assertNotEquals("Ожидается изменение email", oldEmail, testUser.getEmail());
    }

    @Test
    @DisplayName("Изменение email на уже существующий для авторизованного пользователя")
    public void shouldNotChangeUserEmailToExistingEmail() {
        User anotherUser = UserDataGenerator.createUniqueUser();
        userClient.createUser(anotherUser);
        testUser.setEmail(anotherUser.getEmail());
        ValidatableResponse updateUserResponse = userClient.updateUserWithAuth(accessToken, Credentials.from(testUser));
        int statusCode = updateUserResponse.extract().statusCode();
        boolean isUserUpdated = updateUserResponse.extract().path("success");
        String errorMessage = updateUserResponse.extract().path("message");
        assertEquals("Ожидается статус 403", SC_FORBIDDEN, statusCode);
        assertFalse("Ожидается неуспешное обновление данных пользователя", isUserUpdated);
        assertEquals("Ожидается сообщение об ошибке", "User with such email already exists", errorMessage);
    }

    @Test
    @DisplayName("Изменение пароля авторизованного пользователя")
    public void shouldChangeUserPasswordWithAuth() {
        String oldPassword = testUser.getPassword();
        testUser.setPassword("newSecurePassword");
        ValidatableResponse updateUserResponse = userClient.updateUserWithAuth(accessToken, Credentials.from(testUser));
        int statusCode = updateUserResponse.extract().statusCode();
        boolean isUserUpdated = updateUserResponse.extract().path("success");
        assertEquals("Ожидается статус 200", SC_OK, statusCode);
        assertTrue("Ожидается успешное обновление данных пользователя", isUserUpdated);
        assertNotEquals("Ожидается изменение пароля", oldPassword, testUser.getPassword());
    }
}
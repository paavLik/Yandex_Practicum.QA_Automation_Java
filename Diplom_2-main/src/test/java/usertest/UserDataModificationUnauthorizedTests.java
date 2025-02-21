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

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.*;

public class UserDataModificationUnauthorizedTests {
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
    @DisplayName("Изменение email неавторизованного пользователя")
    public void shouldNotChangeEmailWithoutAuth() {
        testUser.setEmail("unauthorizedChange@test.com");
        ValidatableResponse updateUserResponse = userClient.updateUserWithoutAuth(Credentials.from(testUser));
        int statusCode = updateUserResponse.extract().statusCode();
        boolean isUserUpdated = updateUserResponse.extract().path("success");
        String errorMessage = updateUserResponse.extract().path("message");
        assertEquals("Ожидается статус 401", SC_UNAUTHORIZED, statusCode);
        assertFalse("Ожидается неуспешное обновление данных пользователя", isUserUpdated);
        assertEquals("Ожидается сообщение об ошибке", "You should be authorised", errorMessage);
    }

    @Test
    @DisplayName("Изменение пароля неавторизованного пользователя")
    public void shouldNotChangePasswordWithoutAuth() {
        testUser.setPassword("unauthorizedPasswordChange");
        ValidatableResponse updateUserResponse = userClient.updateUserWithoutAuth(Credentials.from(testUser));
        int statusCode = updateUserResponse.extract().statusCode();
        boolean isUserUpdated = updateUserResponse.extract().path("success");
        String errorMessage = updateUserResponse.extract().path("message");
        assertEquals("Ожидается статус 401", SC_UNAUTHORIZED, statusCode);
        assertFalse("Ожидается неуспешное обновление данных пользователя", isUserUpdated);
        assertEquals("Ожидается сообщение об ошибке", "You should be authorised", errorMessage);
    }

    @Test
    @DisplayName("Изменение имени неавторизованного пользователя")
    public void shouldNotChangeNameWithoutAuth() {
        testUser.setName("UnauthorizedChange");
        ValidatableResponse updateUserResponse = userClient.updateUserWithoutAuth(Credentials.from(testUser));
        int statusCode = updateUserResponse.extract().statusCode();
        boolean isUserUpdated = updateUserResponse.extract().path("success");
        String errorMessage = updateUserResponse.extract().path("message");
        assertEquals("Ожидается статус 401", SC_UNAUTHORIZED, statusCode);
        assertFalse("Ожидается неуспешное обновление данных пользователя", isUserUpdated);
        assertEquals("Ожидается сообщение об ошибке", "You should be authorised", errorMessage);
    }
}
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

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class UserLoginPositiveTests {
    private UserClient userClient;
    private User testUser;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        testUser = UserDataGenerator.createUniqueUser();
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Вход пользователя")
    public void shouldLoginSuccessfully() {
        ValidatableResponse createUserResponse = userClient.createUser(testUser);
        ValidatableResponse loginUserResponse = userClient.loginUser(Credentials.from(testUser));
        int statusCode = loginUserResponse.extract().statusCode();
        boolean isUserLoggedIn = loginUserResponse.extract().path("success");
        accessToken = loginUserResponse.extract().path("accessToken");
        assertEquals("Ожидается статус 200", SC_OK, statusCode);
        assertTrue("Ожидается успешный вход", isUserLoggedIn);
    }
}
package usertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.user.Credentials;
import ru.yandex.user.User;
import ru.yandex.user.UserClient;
import ru.yandex.user.UserDataGenerator;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class UserLoginNegativeParameterizedTests {
    private UserClient userClient;
    private final User user;
    private final int expectedStatusCode;
    private final String expectedMessage;

    public UserLoginNegativeParameterizedTests(User user, int expectedStatusCode, String expectedMessage) {
        this.user = user;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {UserDataGenerator.createUserWithEmailOnly(), SC_UNAUTHORIZED, "email or password are incorrect"},
                {UserDataGenerator.createUserWithPasswordOnly(), SC_UNAUTHORIZED, "email or password are incorrect"},
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Вход пользователя с пропущенным обязательным полем")
    public void shouldNotLoginWithMissingField() {
        ValidatableResponse loginUserResponse = userClient.loginUser(Credentials.from(user));
        int statusCode = loginUserResponse.extract().statusCode();
        boolean isUserLoggedIn = loginUserResponse.extract().path("success");
        String actualMessage = loginUserResponse.extract().path("message");
        assertEquals("Ожидается статус " + expectedStatusCode, expectedStatusCode, statusCode);
        assertFalse("Ожидается неуспешный вход", isUserLoggedIn);
        assertEquals("Ожидается сообщение об ошибке", expectedMessage, actualMessage);
    }
}
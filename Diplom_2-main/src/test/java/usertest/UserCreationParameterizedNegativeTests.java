package usertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.user.User;
import ru.yandex.user.UserClient;
import ru.yandex.user.UserDataGenerator;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class UserCreationParameterizedNegativeTests {
    private UserClient userClient;
    private final User user;
    private final int expectedStatusCode;
    private final String expectedMessage;

    public UserCreationParameterizedNegativeTests(User user, int expectedStatusCode, String expectedMessage) {
        this.user = user;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {UserDataGenerator.createUserWithEmailOnly(), SC_FORBIDDEN, "Email, password and name are required fields"},
                {UserDataGenerator.createUserWithPasswordOnly(), SC_FORBIDDEN, "Email, password and name are required fields"}
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Создание пользователя с пропущенным обязательным полем")
    public void shouldNotCreateUserWithMissingField() {
        ValidatableResponse createUserResponse = userClient.createUser(user);
        int statusCode = createUserResponse.extract().statusCode();
        boolean isUserCreated = createUserResponse.extract().path("success");
        String actualMessage = createUserResponse.extract().path("message");
        assertEquals("Ожидается статус " + expectedStatusCode, expectedStatusCode, statusCode);
        assertFalse("Ожидается неуспешное создание пользователя", isUserCreated);
        assertEquals("Ожидается сообщение об ошибке", expectedMessage, actualMessage);
    }
}
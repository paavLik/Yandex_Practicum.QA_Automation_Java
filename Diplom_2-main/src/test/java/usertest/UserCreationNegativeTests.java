package usertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.user.User;
import ru.yandex.user.UserClient;
import ru.yandex.user.UserDataGenerator;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.*;

public class UserCreationNegativeTests {
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
    @DisplayName("Пользователь не может быть создан дважды")
    public void shouldNotCreateDuplicateUser() {
        ValidatableResponse createUserResponse = userClient.createUser(testUser);
        int statusCode = createUserResponse.extract().statusCode();
        boolean isUserCreated = createUserResponse.extract().path("success");
        String actualMessage = createUserResponse.extract().path("message");
        assertEquals("Ожидается статус 403", SC_FORBIDDEN, statusCode);
        assertFalse("Ожидается неуспешное создание пользователя", isUserCreated);
        assertEquals("Ожидается сообщение об ошибке", "User already exists", actualMessage);
    }
}
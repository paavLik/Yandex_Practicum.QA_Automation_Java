package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.courier.CourierClient;
import ru.yandex.courier.CourierCreate;
import ru.yandex.courier.Credentials;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class CredentialsTest {
    private CourierClient courierClient;
    private CourierCreate courier;
    private String courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierCreate.getGeneratorDataCourier();
        courierClient.createCourier(courier);
    }

    @Test
    @DisplayName("Авторизация курьера cо всеми полями")
    @Description("Проверка ответа после входа в систему")
    public void checkAuthorizationWithAllParameters() {
        // Получение ID курьера из ответа от сервера
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Credentials.getCredentials(courier));
        courierId = setCourierIdResponse.extract().path("id").toString();
        // Проверка, Status Code = 200 и поле "id" не пустое
        setCourierIdResponse.assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка ответа после попытки входа, когда в теле запроса передан login = пустое значение")
    public void checkAuthorizationWithOutLogin() {
        // Установка пустого login
        courier.setLogin("");
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Credentials.getCredentials(courier));
        // Проверка, что Status Code = 400 и возвращается ожидаемый текст сообщения
        setCourierIdResponse.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка ответа после попытки входа, когда в теле запроса передан password = пустое значение")
    public void checkAuthorizationWithOutPassword() {
        // Установка пустого password
        courier.setPassword("");
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Credentials.getCredentials(courier));
        // Проверка, что Status Code = 400 и возвращается ожидаемый текст сообщения
        setCourierIdResponse.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без логина, пароля")
    @Description("Проверка ответа после попытки входа, когда в теле запроса переданы параметры login и password = пустое значение")
    public void checkAuthorizationWithOutLoginAndPassword() {
        // Установка пустого login
        courier.setLogin("");
        // Установка пустого password
        courier.setPassword("");
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Credentials.getCredentials(courier));
        // Проверка, что Status Code = 400 и возвращается ожидаемый текст сообщения
        setCourierIdResponse.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера с несуществующим логином")
    @Description("Проверка ответа после попытки входа, когда в теле запроса передан несуществующий логин")
    public void checkAuthorizationWithLoginNotRegistered() {
        // Установка несуществующего login
        courier.setLogin("null");
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Credentials.getCredentials(courier));
        // Проверка, что Status Code = 404 и возвращается ожидаемый текст сообщения
        setCourierIdResponse.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteCourierAfterTest() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }
}
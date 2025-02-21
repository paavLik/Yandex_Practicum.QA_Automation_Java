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

import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {
    private CourierClient courierClient;
    private CourierCreate courier;
    private String courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierCreate.getGeneratorDataCourier();
    }

    @Test
    @DisplayName("Проверка ответа при создании курьера")
    @Description("УЗ курьера успешно создастся при условии, если в теле запроса переданы поля с валидными данными : login, password, firstName")
    public void createCourierWithAllParameters() {
        // Создание курьера с параметрами
        ValidatableResponse courierResponse = courierClient.createCourier(courier);
       // Проверка, Status Code = 201 и поле "ok" не пустое
        courierResponse.assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
        // Получение ID курьера из ответа
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Credentials.getCredentials(courier));
        courierId = setCourierIdResponse.extract().path("id").toString();
    }

    @Test
    @DisplayName("Проверка создания курьера только с обязательными полями")
    @Description("УЗ курьера успешно создастся при условии, если в теле запроса переданы только обязательные поля с валидными данными : login, password")
    public void createCourierWithOutFirstName() {
        // Установка пустого имени для курьера
        courier.setFirstName("");
        // Создание курьера с параметрами
        ValidatableResponse courierResponse = courierClient.createCourier(courier);
        // Проверка, Status Code = 201 и поле "ok" = true
        courierResponse.assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
        // Получение ID курьера из ответа
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Credentials.getCredentials(courier));
        courierId = setCourierIdResponse.extract().path("id").toString();
    }

    @Test
    @DisplayName("Проверка ответа при создании одного и того же курьера дважды")
    @Description("УЗ не создастся, если в БД уже есть курьер с таким логином")
    public void createDuplicateLoginCourier() {
        // Создание курьера с параметрами
        courierClient.createCourier(courier);
        ValidatableResponse courierResponse = courierClient.createCourier(courier);
        // Проверка, Status Code = 409 и возвращается ожидаемый текст сообщения
        courierResponse.assertThat().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
        // Получение ID курьера из ответа
        ValidatableResponse responseCredentials = courierClient.setCourierID(Credentials.getCredentials(courier));
        courierId = responseCredentials.extract().path("id").toString();
    }

    @Test
    @DisplayName("Проверка ответа после создания курьера без пароля")
    @Description("УЗ курьера не создастся, при переданном значении password = пустое значение")
    public void createCourierWithOutPassword() {
        // Установка пустого пароля для курьера
        courier.setPassword("");
        // Создание курьера с параметрами
        ValidatableResponse courierResponse = courierClient.createCourier(courier);
        // Проверка, Status Code = 400 и возвращается ожидаемый текст сообщения
        courierResponse.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Проверка ответа после создания курьера без логина")
    @Description("УЗ курьера не создастся, при переданном значении login = пустое значение")
    public void createCourierWithOutLogin() {
        // Установка пустого логина для курьера
        courier.setLogin("");
        // Создание курьера с параметрами
        ValidatableResponse courierResponse = courierClient.createCourier(courier);
        // Проверка, Status Code = 400 и возвращается ожидаемый текст сообщения
        courierResponse.assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка ответа после создания курьера без логина, пароля, имени")
    @Description("УЗ курьера не создастся, если в теле запроса переданы параметры:login, password, firstName = пустое значение")
    public void createCourierWithOutLoginPasswordFirstName() {
        // Установка пустого логина для курьера
        courier.setLogin("");
        // Установка пустого пароля для курьера
        courier.setPassword("");
        // Установка пустого Имени для курьера
        courier.setFirstName("");
        // Создание курьера с параметрами
        ValidatableResponse courierResponse = courierClient.createCourier(courier);
        // Проверка, Status Code = 400 и возвращается ожидаемый текст сообщения
        courierResponse.assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deleteCourierAfterTest() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }
}
package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.courier.CourierClient;
import ru.yandex.courier.CourierCreate;
import ru.yandex.courier.Credentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CouriereDeleteTest {
    private CourierClient courierClient;
    private CourierCreate courier;
    private String courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Удаление курьера")
    @Description("Проверка ответа после успешного удаления УЗ курьера")
    public void checkDeleteCourier() {
        // Создание курьера с параметрами
        courier = CourierCreate.getGeneratorDataCourier();
        courierClient.createCourier(courier);
        // Получение ID из ответа от сервера
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Credentials.getCredentials(courier));
        courierId = setCourierIdResponse.extract().path("id").toString();
        // Проверка, что Status Code = 200 и поле "id" не пустое
        setCourierIdResponse.assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
        // Удаление курьера и проверка того, что Status Code = 200 и поле "ok" = true
        ValidatableResponse courierResponse = courierClient.deleteCourier(courierId);
        courierResponse.assertThat()
                .statusCode(200)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Удаление курьера без id")
    @Description("Проверка ответа после удаления курьера без параметра id в теле запроса")
    public void deleteCourierWithOutId() {
        ValidatableResponse response = courierClient.deleteCourier("");
        // Проверка, Status Code = 404 и возвращается ожидаемый текст сообщения
        response.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Not Found."));
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим id")
    @Description("Проверка ответа после удаления курьера, если в запросе передан несуществующий id")
    public void deleteCourierWithNonExistentId() {
        ValidatableResponse response = courierClient.deleteCourier("0");
        // Проверка, Status Code = 404 и возвращается ожидаемый текст сообщения
        response.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Курьера с таким id нет."));
    }
}
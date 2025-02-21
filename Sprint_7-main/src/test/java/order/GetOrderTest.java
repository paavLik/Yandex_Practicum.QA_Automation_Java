package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.order.OrderClient;

import static org.hamcrest.Matchers.equalTo;

public class GetOrderTest {

    OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Запрос с несуществующим номером заказа")
    @Description("Проверка ответа после запроса о несуществующем заказе")
    public void checkOrderGetTrackWithNotRegistered() {
        // Отправка запроса с несуществующим номером заказа
        String trackOrder = "7";
        ValidatableResponse response = orderClient.getOrderTrack(trackOrder);
        // Проверка что Status Code = 404 и возвращается ожидаемый текст сообщения
        response.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Заказ не найден"));
    }

    @Test
    @DisplayName("Запрос без номера заказа")
    @Description("Проверка ответа после запроса без номера заказа")
    public void checkOrderGetTrackWithOutId() {
        // Отправка заказа без номра
        String trackOrder = "";
        ValidatableResponse response = orderClient.getOrderTrack(trackOrder);
        // Проверка что Status Code = 400 и возвращается ожидаемый текст сообщения
        response.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }
}
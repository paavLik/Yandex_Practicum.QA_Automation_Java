package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.order.OrderClient;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка ответа, что возвращается список заказов")
    public void getOrderGetList() {
        ValidatableResponse response = orderClient.getListOrders();
        // Проверка, Status Code = 200 и поле "orders" не пустое
        response.assertThat().log().all()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
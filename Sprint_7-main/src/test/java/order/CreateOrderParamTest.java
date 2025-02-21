package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.order.OrderCreate;
import ru.yandex.order.OrderClient;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.utils.Data.*;

@RunWith(Parameterized.class)
public class CreateOrderParamTest {
    private OrderClient orderClient = new OrderClient();
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
    private String trackOrder;

    public CreateOrderParamTest(String firstName, String lastName,
                                String address, String metroStation,
                                String phone,
                                int rentTime, String deliveryDate,
                                String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката {8}")

    public static Object[][] getValueOrderCreate() {
        return new Object[][]{
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, REN_TIME, DELIVERY_DATE, COMMENT, List.of(BLACK)},
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, REN_TIME, DELIVERY_DATE, COMMENT, List.of(GREY)},
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, 1, DELIVERY_DATE, COMMENT, List.of(BLACK, GREY)},
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, 1, DELIVERY_DATE, COMMENT, List.of()},
        };
    }

    @Test
    @DisplayName("Проверка создания заказа")
    public void checkOrderCreate() {
        OrderCreate orderCreate = new OrderCreate(firstName, lastName, address, metroStation, phone, rentTime,
                deliveryDate, comment, color);
        ValidatableResponse responseOrder = orderClient.createNewOrder(orderCreate);
        // Проверка, что Status Code = 201 и поле "track" не пустое
        responseOrder.assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
        // Получение ID заказа
        trackOrder = responseOrder.extract().path("track").toString();
        ValidatableResponse responseTrack = orderClient.getOrderTrack(trackOrder);
        // Проверка, что Status Code = 200 и возвращаются данные о заказе:  выбран или не выбран цвет самоката
        responseTrack.assertThat()
                .statusCode(200)
                .and()
                .body("order.color", equalTo(color));

    }

    @After
    //очистка данных после теста
    public void cleanUp() {
        orderClient.cancelOrder(trackOrder).log().all();
    }
}
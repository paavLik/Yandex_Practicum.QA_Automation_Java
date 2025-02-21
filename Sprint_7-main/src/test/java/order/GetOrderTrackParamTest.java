package order;

import io.qameta.allure.Description;
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
public class GetOrderTrackParamTest {
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

    public GetOrderTrackParamTest(String firstName, String lastName,
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

    @Parameterized.Parameters(name = "Имя {0}, Фамилия {1}, Адрес {2}, Станция метро {3}, Телефон {4}, " +
            "Дата доставки {5}, Срок аренды {6},Комментарий {7}, Цвет самоката {8}")


    public static Object[][] getDataOrderCreate() {
        return new Object[][]{
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, REN_TIME, DELIVERY_DATE, COMMENT, List.of(BLACK)},
        };
    }

    @Test
    @DisplayName("Получение заказа по его номеру")
    @Description("Проверка того, что ответ возвращает объект с заказом")
    public void checkOrderGetTrack() {
        OrderCreate orderCreate = new OrderCreate(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse responseOrder = orderClient.createNewOrder(orderCreate);
        // Проверка, что Status Code = 201 и поле "track" не пустое
        responseOrder.assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
        // Получение ID заказа
        trackOrder = responseOrder.extract().path("track").toString();
        ValidatableResponse responseTrack = orderClient.getOrderTrack(trackOrder);
        // Проверка, что Status Code = 200 и возвращаются данные созданного заказа
        responseTrack.assertThat()
                .statusCode(200)
                .and()
                .body("order.firstName", equalTo(firstName))
                .body("order.lastName", equalTo(lastName))
                .body("order.metroStation", equalTo(metroStation))
                .body("order.phone", equalTo(phone))
                .body("order.rentTime", equalTo(rentTime))
                .body("order.deliveryDate", equalTo(deliveryDate))
                .body("order.comment", equalTo(comment))
                .body("order.firstName", equalTo(firstName))
                .body("order.color", equalTo(color));
    }

    @After
    // очистка данных после теста
    public void cleanUp() {
        orderClient.cancelOrder(trackOrder).log().all();
    }
}
package ru.yandex.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.utils.Config;


import static io.restassured.RestAssured.given;

public class OrderClient extends Config {

    public final String POST_ORDER_CREATE = "/api/v1/orders";//API Создание заказа
    private final String PUT_ORDER_CANCEL = "/api/v1/orders/cancel";//API Отменить заказ
    private final String GET_LIST_ORDER = "/api/v1/orders"; //API Получение списка заказов
    private final String GET_ORDER_TRACK = "/api/v1/orders/track"; //API Получить заказ по его номеру

    @Step("Создание заказа")
    public ValidatableResponse createNewOrder(OrderCreate order) {
        return given().log().all()
                .spec(getSpec())
                .body(order)
                .when()
                .post(POST_ORDER_CREATE)
                .then().log().all();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getListOrders() {
        return given().log().all()
                .spec(getSpec())
                .when()
                .get(GET_LIST_ORDER)
                .then().log().all();
    }

    @Step("Отменить заказ")
    public ValidatableResponse cancelOrder(String trackOrder) {
        return given().log().all()
                .spec(getSpec())
                .body(trackOrder)
                .when()
                .put(PUT_ORDER_CANCEL)
                .then().log().all();
    }

    @Step("Получить заказ по его номеру")
    public ValidatableResponse getOrderTrack(String trackOrder) {
        return given().log().all()
                .spec(getSpec())
                .queryParam("t", trackOrder)
                .get(GET_ORDER_TRACK)
                .then().log().all();
    }
}
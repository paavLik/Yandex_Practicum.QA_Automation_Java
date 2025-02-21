package ru.yandex.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.BaseClient;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    private static final String PATH = "/api/orders/";

    @Step("Создать заказ с авторизованным пользователем")
    public ValidatableResponse createOrderWithAuth(String accessToken, Order order) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Создать заказ с неавторизованным пользователем")
    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Создать заказ без ингредиентов")
    public ValidatableResponse createOrderWithoutIngredients(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Получить заказы для авторизованного пользователя")
    public ValidatableResponse getOrdersWithAuth(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(PATH)
                .then();
    }

    @Step("Получить заказы для неавторизованного пользователя")
    public ValidatableResponse getOrdersWithoutAuth() {
        return given()
                .spec(getSpec())
                .when()
                .get(PATH)
                .then();
    }
}
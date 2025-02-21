package ru.yandex.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.utils.Config;

import static io.restassured.RestAssured.given;

public class CourierClient extends Config {
    private static final String PATH_COURIER_CREATE = "/api/v1/courier/";
    private static final String PATH_LOGIN = "/api/v1/courier/login/";
    private static final String PATH_DELETE = "/api/v1/courier/";

    @Step("Создать курьера")
    public ValidatableResponse createCourier(CourierCreate courier) {
        return given().log().all()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH_COURIER_CREATE)
                .then();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse setCourierID(Credentials credentials) {
        return given().log().all()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(PATH_LOGIN)
                .then().log().all();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(String courierId) {
        return given()
                .spec(getSpec())
                .when()
                .delete(PATH_DELETE + courierId)
                .then().log().all();
    }
}
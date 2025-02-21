package ru.yandex.api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static ru.yandex.api.Config.*;

public class ApiHandler {

    // Создание базового запроса без указания типа контента
    private RequestSpecification createBaseRequest() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }

    // Создание базового запроса с указанием типа контента
    private RequestSpecification createBaseRequest(String contentType) {
        return new RequestSpecBuilder()
                .addHeader("Content-type", contentType)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }

    // Выполнение запроса на авторизацию пользователя
    private Response performLogin(String email, String password) {
        return given(this.createBaseRequest("application/json"))
                .body(new UserInfo(email, password))
                .when()
                .post(API_LOGIN);
    }

    // Выполнение запроса на удаление пользователя
    private Response performUserDeletion(String token) {
        return given(this.createBaseRequest())
                .auth().oauth2(token)
                .delete(API_DELETE_USER);
    }

    @Step("Удаление тестового пользователя")
    public void deleteTestUser(String email, String password) {
        Response response = performLogin(email, password);

        if (response.getStatusCode() != 200) return;

        String token = response.body().as(UserResponse.class).getAccessToken().split(" ")[1];
        performUserDeletion(token);
    }

    @Step("Создание пользователя")
    public void createUser(String name, String email, String password) {
        Response response = given(this.createBaseRequest("application/json"))
                .body(new UserInfo(email, password, name))
                .when()
                .post(API_CREATE_USER);
    }
}

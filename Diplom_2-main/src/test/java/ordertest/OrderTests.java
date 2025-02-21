package ordertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.order.Order;
import ru.yandex.order.OrderClient;
import ru.yandex.user.User;
import ru.yandex.user.UserClient;
import ru.yandex.user.UserDataGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class OrderTests {
    private OrderClient orderClient;
    private String accessToken;
    private UserClient userClient;
    private User testUser;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        testUser = UserDataGenerator.createUniqueUser();
        ValidatableResponse createUserResponse = userClient.createUser(testUser);
        accessToken = createUserResponse.extract().path("accessToken");
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем")
    public void shouldCreateOrderWithAuth() {
        Order order = new Order();
        List<String> ingredients = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(ingredients);
        ValidatableResponse createOrderResponse = orderClient.createOrderWithAuth(accessToken, order);
        int statusCode = createOrderResponse.extract().statusCode();
        boolean isOrderCreated = createOrderResponse.extract().path("success");
        assertEquals("Ожидается статус 200", SC_OK, statusCode);
        assertTrue("Ожидается успешное создание заказа", isOrderCreated);
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем")
    public void shouldNotCreateOrderWithoutAuth() {
        Order order = new Order();
        List<String> ingredients = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(ingredients);
        ValidatableResponse createOrderResponse = orderClient.createOrderWithoutAuth(order);
        int statusCode = createOrderResponse.extract().statusCode();
        boolean isOrderCreated = createOrderResponse.extract().path("success");
        assertEquals("Ожидается статус 401", SC_UNAUTHORIZED, statusCode);
        assertFalse("Ожидается неуспешное создание заказа", isOrderCreated);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void shouldNotCreateOrderWithoutIngredients() {
        ValidatableResponse createOrderResponse = orderClient.createOrderWithoutIngredients(accessToken);
        int statusCode = createOrderResponse.extract().statusCode();
        boolean isOrderCreated = createOrderResponse.extract().path("success");
        String errorMessage = createOrderResponse.extract().path("message");
        assertEquals("Ожидается статус 400", SC_BAD_REQUEST, statusCode);
        assertFalse("Ожидается неуспешное создание заказа", isOrderCreated);
        assertEquals("Ожидается сообщение об ошибке", "Ingredient ids must be provided", errorMessage);
    }

    @Test
    @DisplayName("Создание заказа с неверным ингредиентом")
    public void shouldNotCreateOrderWithInvalidIngredient() {
        Order order = new Order();
        List<String> ingredients = new ArrayList<>(Arrays.asList("invalid_ingredient_id", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(ingredients);
        ValidatableResponse createOrderResponse = orderClient.createOrderWithAuth(accessToken, order);
        int statusCode = createOrderResponse.extract().statusCode();
        assertEquals("Ожидается статус 500", SC_INTERNAL_SERVER_ERROR, statusCode);
    }

    @Test
    @DisplayName("Получение заказов авторизованным пользователем")
    public void shouldGetOrdersWithAuth() {
        Order order = new Order();
        List<String> ingredients = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(ingredients);
        orderClient.createOrderWithAuth(accessToken, order);
        ValidatableResponse getOrdersResponse = orderClient.getOrdersWithAuth(accessToken);
        int statusCode = getOrdersResponse.extract().statusCode();
        boolean areOrdersRetrieved = getOrdersResponse.extract().path("success");
        assertEquals("Ожидается статус 200", SC_OK, statusCode);
        assertTrue("Ожидается успешное получение заказов", areOrdersRetrieved);
    }

    @Test
    @DisplayName("Получение заказов неавторизованным пользователем")
    public void shouldNotGetOrdersWithoutAuth() {
        ValidatableResponse getOrdersResponse = orderClient.getOrdersWithoutAuth();
        int statusCode = getOrdersResponse.extract().statusCode();
        boolean areOrdersRetrieved = getOrdersResponse.extract().path("success");
        String errorMessage = getOrdersResponse.extract().path("message");
        assertEquals("Ожидается статус 401", SC_UNAUTHORIZED, statusCode);
        assertFalse("Ожидается неуспешное получение заказов", areOrdersRetrieved);
        assertEquals("Ожидается сообщение об ошибке", "You should be authorised", errorMessage);
    }
}
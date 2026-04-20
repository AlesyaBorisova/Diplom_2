package tests;

import base.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;
import model.User;
import org.junit.Before;
import org.junit.Test;
import services.OrderClient;
import services.UserClient;
import utils.OrderGenerator;
import utils.UserGenerator;


import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class OrderTests extends BaseTest {

    UserClient userClient = new UserClient();
    OrderClient orderClient = new OrderClient();

    private User user;
    private String token;

    @Before
    public void setUpUser() {
        user = UserGenerator.createUser();
        userClient.createUser(user);

        token = userClient.loginUser(user)
                .then()
                .extract()
                .path("accessToken");
    }

    @Test
    public void createOrderWithAuth() {
        Order order = OrderGenerator.createOrder();
        createOrderWithAuthStep(order, token);
    }

    @Step("Создание заказа с авторизацией")
    public void createOrderWithAuthStep(Order order, String token) {
        Response response = orderClient.createOrderWithAuth(order, token);

        response.then().statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    public void createOrderWithoutAuth() {
        Order order = OrderGenerator.createOrder();

        Response response = orderClient.createOrderWithoutAuth(order);

        response.then().statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    public void createOrderWithIngredients() {
        Order order = OrderGenerator.createOrder();
        createOrderWithIngredientsStep(order);
    }

    @Step("Создание заказа с ингредиентами")
    public void createOrderWithIngredientsStep(Order order) {
        Response response = orderClient.createOrderWithoutAuth(order);

        response.then().statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    public void createOrderWithoutIngredients() {
        Order order = OrderGenerator.createOrderWithoutIngredients();
        createOrderWithoutIngredientsStep(order);
    }

    @Step("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredientsStep(Order order) {
        Response response = orderClient.createOrderWithoutAuth(order);

        response.then().statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    public void createOrderWithWrongHash() {
        Order order = OrderGenerator.createOrderWithWrongHash();
        createOrderWithWrongHashStep(order);
    }

    @Step("Создание заказа с неверным хэшем ингредиентов")
    public void createOrderWithWrongHashStep(Order order) {
        Response response = orderClient.createOrderWithoutAuth(order);

        response.then().statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}

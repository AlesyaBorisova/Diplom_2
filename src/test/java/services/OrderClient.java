package services;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Order;

public class OrderClient {

    private static final String CREATE_ORDER = "/api/orders";

    @Step("Создание заказа с авторизацией: {order.ingredients}")

    public Response createOrderWithAuth(Order order, String token) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(order)
                .post(CREATE_ORDER);
    }

    @Step("Создание заказа без авторизации: {order.ingredients}")

    public Response createOrderWithoutAuth(Order order) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(order)
                .post(CREATE_ORDER);
    }
}

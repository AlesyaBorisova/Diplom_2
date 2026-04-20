package services;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.User;


public class UserClient {

    private static final String CREATE_ENDPOINT = "/api/auth/register";
    private static final String LOGIN_ENDPOINT = "/api/auth/login";


    @Step("Создать уникального пользователя c email: {user.email}")

    public Response createUser(User user) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(CREATE_ENDPOINT);
    }

    @Step("Логин пользователя c email: {user.email}")

    public Response loginUser(User user) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(user)
                .post(LOGIN_ENDPOINT);
    }

}

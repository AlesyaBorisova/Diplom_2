package tests;

import base.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.User;
import org.junit.Test;
import services.UserClient;
import utils.UserGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterUserTests extends BaseTest {

    UserClient userClient = new UserClient();

    @Test
    public void createUniqueUserTest() {
        User user = UserGenerator.createUser();
        createUserStep(user);
    }

    @Step("Создание уникального пользователя")
    public void createUserStep(User user) {
        Response response = userClient.createUser(user);

        response.then().statusCode(SC_OK)
                .body("success", equalTo(true));

    }

    @Test
    public void createExistingUserTest() {
        User user = UserGenerator.createExistingUser();
        userClient.createUser(user);
        createExistingUserStep(user);
    }

    @Step("Создание пользователя, который уже существует")
    public void createExistingUserStep(User user) {
        Response response = userClient.createUser(user);

        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));

    }

    @Test
    public void createUserWithoutPassword() {
        User user = UserGenerator.createUserWithoutPassword();
        createUserWithoutPasswordStep(user);
    }

    @Step("Создание пользователя без пароля")
    public void createUserWithoutPasswordStep(User user) {
        Response response = userClient.createUser(user);

        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void createUserWithoutEmail() {
        User user = UserGenerator.createUserWithoutEmail();
        createUserWithoutEmailStep(user);
    }

    @Step("Создание пользователя без email")
    public void createUserWithoutEmailStep(User user) {
        Response response = userClient.createUser(user);

        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void createUserWithoutName() {
        User user = UserGenerator.createUserWithoutName();
        createUserWithoutNameStep(user);
    }

    @Step("Создание пользователя без имени")
    public void createUserWithoutNameStep(User user) {
        Response response = userClient.createUser(user);

        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}





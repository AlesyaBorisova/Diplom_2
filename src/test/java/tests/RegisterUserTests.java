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
        createUserWithInvalidDataStep(user);
    }

    @Test
    public void createUserWithoutEmail() {
        User user = UserGenerator.createUserWithoutEmail();
        createUserWithInvalidDataStep(user);
    }

    @Test
    public void createUserWithoutName() {
        User user = UserGenerator.createUserWithoutName();
        createUserWithInvalidDataStep(user);
    }

    @Step("Создание пользователя с некорректными данными: email={user.email}, password={user.password}, name={user.name}")
    public void createUserWithInvalidDataStep(User user) {
        Response response = userClient.createUser(user);

        response.then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}





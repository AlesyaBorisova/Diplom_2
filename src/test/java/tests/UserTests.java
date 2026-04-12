package tests;

import base.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.User;
import org.junit.Test;
import services.UserClient;
import utils.UserGenerator;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserTests extends BaseTest {

    UserClient userClient = new UserClient();

    @Test
    public void createUniqueUserTest() {
        User user = UserGenerator.createUser();
        createUserStep(user);
    }

    @Step("Создание уникального пользователя")
    public void createUserStep(User user) {
        Response response = userClient.createUser(user);

        response.then().statusCode(200)
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
                .statusCode(403)
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
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }


    @Test
    public void loginUserTest() {
        User user = new User("test" + System.currentTimeMillis() + "@mail.com", "1234", "name");
        userClient.createUser(user);
        loginUserStep(user);

    }

    @Step("Вход под существующим пользователем")
    public void loginUserStep(User user) {
        Response response = userClient.loginUser(user);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void loginWithWrongUserTest() {

        User user = UserGenerator.createWrongUser();
        loginWithWrongUserStep(user);
    }

    @Step("Вход с неверным логином и паролем")
    public void loginWithWrongUserStep(User user) {
        Response response = userClient.loginUser(user);

        response.then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}


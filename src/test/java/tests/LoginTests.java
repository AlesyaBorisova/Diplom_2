package tests;

import base.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.User;
import org.junit.Before;
import org.junit.Test;
import services.UserClient;
import utils.UserGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginTests extends BaseTest {

    UserClient userClient = new UserClient();

    private User user;

    @Before
    public void initUser() {
        user = UserGenerator.createUser();
        userClient.createUser(user);
    }

    @Test
    public void loginUserTest() {
        loginUserStep(user);
    }

    @Step("Успешный логин")
    public void loginUserStep(User user) {
        Response response = userClient.loginUser(user);

        response.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    public void loginWithWrongEmailTest() {
        User wrongEmailUser = new User(
                "wrong" + System.currentTimeMillis() + "@mail.com",
                user.getPassword(),
                user.getName()
        );

        loginWithInvalidDataStep(wrongEmailUser);
    }

    @Step("Логин с неверными данными: email={user.email}, password={user.password}")
    public void loginWithInvalidDataStep(User user) {
        Response response = userClient.loginUser(user);

        response.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void loginWithWrongPasswordTest() {
        User wrongPasswordUser = new User(
                user.getEmail(),
                "wrongPassword",
                user.getName()
        );

        loginWithInvalidDataStep(wrongPasswordUser);
    }

}


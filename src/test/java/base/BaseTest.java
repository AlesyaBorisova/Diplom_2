package base;

import io.restassured.RestAssured;
import org.junit.Before;

public class BaseTest {

    protected static final String BASE_URL = "https://stellarburgers.education-services.ru/";


    @Before

    public void setUp() {
        RestAssured.baseURI = BASE_URL;

    }
}

package base;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class BaseTest {

    protected static final String BASE_URL = "https://stellarburgers.education-services.ru/";


    @BeforeClass

    public static void setUp() {
        RestAssured.baseURI = BASE_URL;

    }
}

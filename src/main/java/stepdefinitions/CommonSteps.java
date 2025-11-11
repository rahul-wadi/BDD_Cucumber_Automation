package stepdefinitions;

import io.restassured.response.Response;
import org.testng.Assert;

public class CommonSteps {

    public static void verifyStatusCode(Response response, int expectedCode) {
        Assert.assertEquals(response.getStatusCode(), expectedCode, "Status code mismatch!");
    }

    public static void verifyBodyContains(Response response, String expectedKey) {
        Assert.assertTrue(response.getBody().asString().contains(expectedKey),
                "Response body does not contain expected key: " + expectedKey);
    }
}

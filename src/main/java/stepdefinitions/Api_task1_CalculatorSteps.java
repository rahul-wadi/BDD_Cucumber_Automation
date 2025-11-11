package stepdefinitions;

import Utilities.SoapUtils;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.*;

public class Api_task1_CalculatorSteps {
    public String method;
    public Response response;
    public int intA, intB;

    @Given("the calculator method is {string}")
    public void setMethod(String methodName) {
        this.method = methodName;
    }

    @And("the input values are {int} and {int}")
    public void setInputValues(int a, int b) {
        this.intA = a;
        this.intB = b;
    }

    @When("I send the SOAP request")
    public void sendSoapRequest() {
        String body = SoapUtils.buildSoapRequest(method, intA, intB);
        String soapAction = "http://tempuri.org/" + method;
        response = SoapUtils.sendSoapRequest(soapAction, body);
    }

    @Then("the response status should be {int}")
    public void verifyStatusCode(int expectedStatus) {
        Assert.assertEquals(response.statusCode(), expectedStatus);
    }

    @And("the result tag {string} should be {string}")
    public void verifyResult(String tagName, String expectedValue) {
        String actual = SoapUtils.extractValue(response, tagName);
        Assert.assertEquals(actual, expectedValue, "Verification failed for " + tagName);
    }
}

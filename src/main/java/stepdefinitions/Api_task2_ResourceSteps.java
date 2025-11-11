package stepdefinitions;

import Utilities.RestUtils;
import config.Constants;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;

public class Api_task2_ResourceSteps {

    private Response response;

    @Given("the API base URL is set")
    public void setBaseURL() {
        Reporter.log("Base URL: " + Constants.BASE_URL, true);
    }

    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        response = RestUtils.getResponse(endpoint);
    }

    @Then("I should receive a list of resources")
    public void verifyResponseStatus() {
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
    }

    @Then("I print the number of resources returned")
    public void printNumberOfResources() {
        int count = response.jsonPath().getList("$").size();
        Reporter.log("Number of resources returned: " + count, true);
    }
}

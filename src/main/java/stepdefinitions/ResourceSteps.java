package stepdefinitions;

import Utilities.RestUtils;
import Utilities.TestDataBuilder;
import io.cucumber.java.en.*;
import org.json.simple.JSONObject;

public class ResourceSteps {

    Response response;
    String endpoint;

    @Given("User sets resource endpoint as {string}")
    public void user_sets_resource_endpoint_as(String resource) {
        endpoint = ConfigManager.BASE_URL + resource;
    }

    @When("User retrieves all resources")
    public void user_retrieves_all_resources() {
        response = RestUtils.get(endpoint);
    }

    @Then("Verify response code is {int}")
    public void verify_response_code_is(int code) {
        CommonSteps.verifyStatusCode(response, code);
    }

    @And("Verify response contains at least {int} items")
    public void verify_response_contains_items(int count) {
        int size = response.jsonPath().getList("$").size();
        org.testng.Assert.assertTrue(size >= count, "Expected at least " + count + " items but got " + size);
    }

    @When("User gets resource with id {int}")
    public void user_gets_resource_with_id(Integer id) {
        response = RestUtils.getById(endpoint, id);
    }

    @When("User updates resource with id {int}")
    public void user_updates_resource_with_id(Integer id) {
        JSONObject body = TestDataBuilder.updatePostData();
        response = RestUtils.put(endpoint, id, body.toString());
    }

    @When("User deletes resource with id {int}")
    public void user_deletes_resource_with_id(Integer id) {
        response = RestUtils.delete(endpoint, id);
    }

    @When("User creates a new resource")
    public void user_creates_a_new_resource() {
        JSONObject body = TestDataBuilder.createPostData();
        response = RestUtils.post(endpoint, body.toString());
    }

    @Then("Verify response body contains {string}")
    public void verify_response_body_contains(String key) {
        CommonSteps.verifyBodyContains(response, key);
    }
}

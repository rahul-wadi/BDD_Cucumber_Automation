package stepdefinitions;

import Utilities.RestUtils;
import Utilities.TestDataBuilder;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

public class Api_task3_PetSteps {

    public static Response postResponse;
    public static Response getResponse;
    public final String BASE_URL = "https://petstore.swagger.io/v2";

    @Given("I create a pet using the POST API")
    public void i_create_a_pet_using_post_api() {
        postResponse = RestUtils.postRequest(BASE_URL, "/pet", TestDataBuilder.createPetBody().toString());
        Assert.assertEquals(postResponse.statusCode(), 200, "Pet creation failed!");
    }

    @When("I retrieve the pet details using the GET API")
    public void i_retrieve_pet_details_using_get_api() {
        getResponse = RestUtils.getRequest(BASE_URL, "/pet/12345");
    }

    @Then("the status code should be 200")
    public void the_status_code_should_be_200() {
        Assert.assertEquals(getResponse.statusCode(), 200);
    }

    @Then("the content type should be {string}")
    public void the_content_type_should_be(String expectedType) {
        Assert.assertTrue(getResponse.contentType().contains(expectedType));
    }

    @Then("the category name should be {string}")
    public void the_category_name_should_be(String expectedCategory) {
        String actual = getResponse.jsonPath().getString("category.name");
        Assert.assertEquals(actual, expectedCategory);
    }

    @Then("the pet name should be {string}")
    public void the_pet_name_should_be(String expectedName) {
        String actual = getResponse.jsonPath().getString("name");
        Assert.assertEquals(actual, expectedName);
    }

    @Then("the pet status should be {string}")
    public void the_pet_status_should_be(String expectedStatus) {
        String actual = getResponse.jsonPath().getString("status");
        Assert.assertEquals(actual, expectedStatus);
    }
}

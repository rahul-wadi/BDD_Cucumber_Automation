package stepdefinitions;

import Utilities.RestUtils;
import Utilities.TestDataBuilder;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.List;
import java.util.Map;

public class Api_task4_BookSteps {

    Response response;

    static String orderId;

    @Given("I make a GET request to {string}")
    public void i_make_a_get_request_to(String endpoint) {
        response = RestUtils.getRequest(endpoint);
    }

    @Then("the status code should be {int}")
    public void the_status_code_should_be(Integer expectedStatus) {
        Assert.assertEquals(response.getStatusCode(), expectedStatus.intValue());
    }

    @Then("the response should contain {string} as {string}")
    public void the_response_should_contain_as(String key, String value) {
        Assert.assertEquals(response.jsonPath().getString(key), value);
    }

    @Then("there should be at least one book in the list")
    public void there_should_be_at_least_one_book_in_the_list() {
        List<Object> books = response.jsonPath().getList("");
        Assert.assertTrue(books.size() > 0);
    }

    @Then("I print the title and type of all books")
    public void i_print_the_title_and_type_of_all_books() {
        List<Map<String, Object>> books = response.jsonPath().getList("");
        for (Map<String, Object> book : books) {
            Reporter.log("Title: " + book.get("name") + ", Type: " + book.get("type"), true);
        }
    }

    @Then("all books should be of type {string}")
    public void all_books_should_be_of_type(String type) {
        List<Map<String, Object>> books = response.jsonPath().getList("");
        for (Map<String, Object> book : books) {
            Assert.assertEquals(book.get("type"), type);
        }
    }

    @Then("the book ID should be {int}")
    public void the_book_id_should_be(Integer id) {
        Assert.assertEquals(response.jsonPath().getInt("id"), id.intValue());
        Reporter.log("Title: " + response.jsonPath().getString("name"), true);
        Reporter.log("Type: " + response.jsonPath().getString("type"), true);
        Reporter.log("Available: " + response.jsonPath().getBoolean("available"), true);
    }

    @Given("I generate an access token")
    public void i_generate_an_access_token() {
        response = RestUtils.postRequest("/api-clients", TestDataBuilder.createClientPayload());
    }

    @Then("I store and print the access token")
    public void i_store_and_print_the_access_token() {
        String token = response.jsonPath().getString("accessToken");
        RestUtils.token = token;
        Assert.assertNotNull(token);
        Reporter.log("Access Token: " + token, true);
    }

    @Given("I have a valid access token")
    public void i_have_a_valid_access_token() {
        // If no token, generate one using existing utility
        if (RestUtils.token == null) {
            Response tokenResponse = RestUtils.postRequest("/api-clients", TestDataBuilder.createClientPayload());
            Assert.assertEquals(tokenResponse.getStatusCode(), 201);
            RestUtils.token = tokenResponse.jsonPath().getString("accessToken");
            Reporter.log("Generated Token: " + RestUtils.token, true);
        }
    }

    @When("I place an order for bookId {int} and customer {string}")
    public void i_place_an_order_for_book_id_and_customer(Integer bookId, String customerName) {
        response = RestUtils.postRequestWithAuth("/orders", TestDataBuilder.createOrderPayload(bookId, customerName));
    }

    @Then("I should see an order ID in the response")
    public void i_should_see_an_order_id_in_the_response() {
        orderId = response.jsonPath().getString("orderId");
        Assert.assertNotNull(orderId);
        Reporter.log("Order ID Created: " + orderId, true);
    }

    @When("I retrieve all orders")
    public void i_retrieve_all_orders() {
        response = RestUtils.getRequestWithAuth("/orders");
    }

    @Then("I print all order details")
    public void i_print_all_order_details() {
        List<Map<String, Object>> orders = response.jsonPath().getList("");
        for (Map<String, Object> order : orders) {
            Reporter.log("OrderID: " + order.get("id")
                    + ", BookID: " + order.get("bookId")
                    + ", Customer: " + order.get("customerName"), true);
        }
    }

    @When("I retrieve the order details by the stored order ID")
    public void i_retrieve_the_order_details_by_the_stored_order_id() {
        response = RestUtils.getRequestWithAuth("/orders/" + orderId);
    }

    @Then("the order ID in the response should match the stored ID")
    public void the_order_id_in_the_response_should_match_the_stored_id() {
        Assert.assertEquals(response.jsonPath().getString("id"), orderId);
        Reporter.log("Order Details: " + response.toString(), true);
    }

    @When("I update the order customer name to {string}")
    public void i_update_the_order_customer_name_to(String newName) {
        response = RestUtils.putRequest("/orders/" + orderId, TestDataBuilder.updateOrderPayload(newName));
    }

    @Then("the order details should reflect the updated name")
    public void the_order_details_should_reflect_the_updated_name() {
        Response updatedResponse = RestUtils.getRequestWithAuth("/orders/" + orderId);
        Assert.assertEquals(updatedResponse.getStatusCode(), 200);
        Assert.assertEquals(updatedResponse.jsonPath().getString("customerName"), "Jane Doe");
        Reporter.log("Updated Order: " + updatedResponse.asPrettyString(), true);
    }

    @When("I cancel the order by its ID")
    public void i_cancel_the_order_by_its_id() {
        response = RestUtils.deleteRequest("/orders/" + orderId);
    }

    @Then("retrieving the same order should return 404")
    public void retrieving_the_same_order_should_return_404() {
        Response verifyResponse = RestUtils.getRequestWithAuth("/orders/" + orderId);
        Assert.assertEquals(verifyResponse.getStatusCode(), 404);
    }

    // Negative Test 1
    @When("I place an order with invalid bookId {int}")
    public void i_place_an_order_with_invalid_book_id(Integer invalidBookId) {
        response = RestUtils.postRequestWithAuth("/orders", TestDataBuilder.createOrderPayload(invalidBookId, "Invalid Book"));
    }

    @Then("the error message should be {string}")
    public void the_error_message_should_be(String expectedMessage) {
        String actualMessage = response.jsonPath().getString("error");
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    // Negative Test 2
    @When("I retrieve order with invalid ID {int}")
    public void i_retrieve_order_with_invalid_id(Integer invalidOrderId) {
        response = RestUtils.getRequestWithAuth("/orders/" + invalidOrderId);
    }
}

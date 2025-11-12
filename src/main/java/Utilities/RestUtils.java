package Utilities;

import io.restassured.*;
import io.restassured.response.*;
import io.restassured.specification.*;
import org.testng.*;

import java.io.*;
import java.util.*;

import static io.restassured.RestAssured.*;

public class RestUtils {

    private static final String BASE_URL = "https://simple-books-api.glitch.me";
    public static String token;

    public static Response get(String endpoint) {
        return given().when().get(endpoint);
    }

    public static Response getById(String endpoint, int id) {
        return given().when().get(endpoint + "/" + id);
    }

    public static Response post(String endpoint, Object body) {
        return given().header("Content-Type", "application/json")
                .body(body)
                .when().post(endpoint);
    }

    public static Response put(String endpoint, int id, Object body) {
        return given().header("Content-Type", "application/json")
                .body(body)
                .when().put(endpoint + "/" + id);
    }

    public static Response delete(String endpoint, int id) {
        return given().when().delete(endpoint + "/" + id);
    }

    public static Response postRequest(String baseURI, String endpoint, Object body) {
        RestAssured.baseURI = baseURI;
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response getRequest(String baseURI, String endpoint) {
        RestAssured.baseURI = baseURI;
        return given()
                .header("Accept", "application/json")
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response getRequest(String endpoint) {
        return given()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response getRequestWithAuth(String endpoint) {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response postRequest(String endpoint, Object body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response postRequestWithAuth(String endpoint, Object body) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response putRequest(String endpoint, Object body) {
        return given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response deleteRequest(String endpoint) {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }

    public static void verifyResponseDetails(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status Code Mismatch!");
        Assert.assertTrue(response.getStatusLine().contains("OK"), "Status Line Mismatch!");
        Assert.assertEquals(response.getContentType(), "application/json", "Content-Type mismatch!");
        Assert.assertNotNull(response.getHeader("Content-Encoding"), "Content-Encoding not found!");
    }

    public static void verifySuccessCode(Response response) {
        String success = response.jsonPath().getString("status");
        Assert.assertEquals(success, "success", "Success Code mismatch!");
    }

    public static Response post(String endpoint, String body) {
        return given()
                .auth().preemptive().basic(TestBase.USER_EMAIL, TestBase.API_TOKEN)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(TestBase.BASE_URL + endpoint);
    }

    public static Response put(String endpoint, String body) {
        return given()
                .auth().preemptive().basic(TestBase.USER_EMAIL, TestBase.API_TOKEN)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put(TestBase.BASE_URL + endpoint);
    }

    public static Response get(String endpoint, Map<String, String> params) {
        RequestSpecification req = given()
                .auth().preemptive().basic(TestBase.USER_EMAIL, TestBase.API_TOKEN);
        if (params != null) req.queryParams(params);
        return req.when().get(TestBase.BASE_URL + endpoint);
    }

    public static Response delete(String endpoint) {
        return given()
                .auth().preemptive().basic(TestBase.USER_EMAIL, TestBase.API_TOKEN)
                .when()
                .delete(TestBase.BASE_URL + endpoint);
    }

    public static Response attach(String endpoint, File file) {
        return given()
                .auth().preemptive().basic(TestBase.USER_EMAIL, TestBase.API_TOKEN)
                .header("X-Atlassian-Token", "no-check")
                .multiPart("file", file)
                .when()
                .post(TestBase.BASE_URL + endpoint);
    }

}

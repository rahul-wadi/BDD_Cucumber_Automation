package Utilities;

import io.restassured.response.Response;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.*;

public class TestDataBuilder {

    public static JSONObject createPostData() {
        JSONObject json = new JSONObject();
        json.put("userId", 10);
        json.put("id", 201);
        json.put("title", "Automation Created Post");
        json.put("body", "This post is created via RestAssured test.");
        return json;
    }

    public static JSONObject updatePostData() {
        JSONObject json = new JSONObject();
        json.put("id", 1);
        json.put("title", "Updated Title");
        json.put("body", "Updated body text");
        json.put("userId", 1);
        return json;
    }

    public static JSONObject createPetBody() {
        JSONObject category = new JSONObject();
        category.put("id", 1);
        category.put("name", "dog");

        JSONObject tag = new JSONObject();
        tag.put("id", 0);
        tag.put("name", "string");

        JSONObject body = new JSONObject();
        body.put("id", 12345);
        body.put("category", category);
        body.put("name", "snoopie");
        body.put("photoUrls", new String[]{"string"});
        body.put("tags", new Object[]{tag});
        body.put("status", "pending");

        return body;
    }

    public static Map<String, Object> createClientPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("clientName", "TestUser");
        payload.put("clientEmail", "testuser" + System.currentTimeMillis() + "@example.com");
        return payload;
    }

    public static Map<String, Object> createOrderPayload(int bookId, String customerName) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("bookId", bookId);
        payload.put("customerName", customerName);
        return payload;
    }

    public static Map<String, Object> updateOrderPayload(String newName) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("customerName", newName);
        return payload;
    }

    public static String createDefect() {
        JSONObject issue = new JSONObject();
        JSONObject fields = new JSONObject();
        JSONObject project = new JSONObject();
        JSONObject issuetype = new JSONObject();

        project.put("key", TestBase.PROJECT_KEY);
        issuetype.put("name", "Bug");

        fields.put("summary", "API Defect created via automation");
        fields.put("description", "Defect created using REST Assured + Cucumber + TestNG");
        fields.put("project", project);
        fields.put("issuetype", issuetype);
        issue.put("fields", fields);

        Response res = RestUtils.post("/issue", issue.toString());
        res.then().statusCode(201);

        String id = res.jsonPath().getString("id");
        TestBase.ISSUE_ID = id;
        return id;
    }

    public static void updateDefect(String issueId) {
        JSONObject updateBody = new JSONObject();
        JSONObject fields = new JSONObject();
        fields.put("summary", "Updated summary via API");
        fields.put("description", "Updated description field");
        updateBody.put("fields", fields);

        RestUtils.put("/issue/" + issueId, updateBody.toString())
                .then().statusCode(204);
    }

    public static void searchDefect(String issueId) {
        Map<String, String> params = new HashMap<>();
        params.put("jql", "id=" + issueId);
        RestUtils.get("/search", params)
                .then().statusCode(200);
    }

    public static void attachFile(String issueId, File file) {
        RestUtils.attach("/issue/" + issueId + "/attachments", file)
                .then().statusCode(200);
    }

    public static void deleteDefect(String issueId) {
        RestUtils.delete("/issue/" + issueId)
                .then().statusCode(204);
    }
}

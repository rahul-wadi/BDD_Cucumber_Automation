package Utilities;

import org.json.simple.JSONObject;

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
}

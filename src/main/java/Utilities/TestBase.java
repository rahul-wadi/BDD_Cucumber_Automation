package Utilities;

import io.restassured.RestAssured;

public class TestBase {

    public static String BASE_URL = "https://your-domain.atlassian.net/rest/api/3";
    public static String USER_EMAIL = "your_email@domain.com";
    public static String API_TOKEN = "your_api_token";
    public static String PROJECT_KEY = "TEST"; // Jira project key
    public static String ISSUE_ID;

    public static void setup() {
        RestAssured.baseURI = "https://simple-books-api.glitch.me";
    }
}

package Utilities;

public class TestBase {

    public static void setup() {
        RestAssured.baseURI = "https://simple-books-api.glitch.me";
    }
}

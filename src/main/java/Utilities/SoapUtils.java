package Utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SoapUtils {
    public static final String BASE_URL = "http://www.dneonline.com/calculator.asmx";

    public static String buildSoapRequest(String method, int intA, int intB) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "  <soap:Body>"
                + "    <" + method + " xmlns=\"http://tempuri.org/\">"
                + "      <intA>" + intA + "</intA>"
                + "      <intB>" + intB + "</intB>"
                + "    </" + method + ">"
                + "  </soap:Body>"
                + "</soap:Envelope>";
    }

    public static Response sendSoapRequest(String soapAction, String body) {
        return RestAssured.given()
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", soapAction)
                .body(body)
                .post(BASE_URL);
    }

    public static String extractValue(Response response, String tagName) {
        String xml = response.getBody().asString();
        String startTag = "<" + tagName + ">";
        String endTag = "</" + tagName + ">";
        return xml.substring(xml.indexOf(startTag) + startTag.length(), xml.indexOf(endTag));
    }
}

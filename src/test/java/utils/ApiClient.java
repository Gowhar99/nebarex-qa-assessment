package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiClient {

    private static final String URL =
        "https://dev.nebarex.com/file/tracking/tracking-info";

    private static final String API_KEY =
        "A9fK3P2Qx7MZ8JrC5LwH6BvTnE4DYSd";

    public static Response getTrackingInfo() {
        return RestAssured
                .given()
                .header("x-api-key", API_KEY)
                .get(URL);   
    }
}

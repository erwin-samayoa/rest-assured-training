package utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class RestAssuredExtension {

    //private ResponseSpecification responseSpec;

    private Response response;
    private RequestSpecification request;

    public RestAssuredExtension(String uri) {
        RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri(uri).build();
        //responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200).build();
        request = given().spec(requestSpec)
                .contentType(ContentType.JSON);
    }

    public void postJson(String endpoint, JSONObject object) {
         response = request.body(object.toString())
            .post(endpoint);
    }

    public String getBodyAsString() {
        return response.getBody().asString();
    }
}

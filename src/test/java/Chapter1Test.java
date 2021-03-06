import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class Chapter1Test {

    @Test
    public void test_zc_90210() {

        Response response = RestAssured.get("http://api.zippopotam.us/us/90210");

        //System.out.println(response.getBody().asString());
        //assertTrue(response.getBody().asString().contains("Beverly Hills"));
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(), "application/json");
        assertEquals(response.getBody().jsonPath().get("places[0].'place name'").toString(),"Beverly Hills");

    }

    @Test
    public void test_zc_90210_log() {
        //Only printing the response data
        var response = RestAssured.get("http://api.zippopotam.us/us/90210");
        response.then().log().all();
    }

    @Test
    public void test_zc_90210_expect_California() {
        var response = RestAssured.get("http://api.zippopotam.us/us/90210");
        assertEquals(response.body().jsonPath().get("places[0].state"),"California","Error in state response");
    }
}

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class Chapter1Test {

    @Test
    public void test1() {
        Response response = RestAssured.get("http://api.zippopotam.us/us/90210");
        //System.out.println(response.getBody().asString());
        //assertTrue(response.getBody().asString().contains("Beverly Hills"));
        assertEquals("Beverly Hills",response.getBody().jsonPath().get("places[0].'place name'").toString());
    }
}

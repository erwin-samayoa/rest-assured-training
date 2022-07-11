import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


public class GeneralTests {

    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    @BeforeClass
    public void setSpecs() {
        requestSpec = new RequestSpecBuilder().setBaseUri("http://api.zippopotam.us").build();
        responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }


    @Test
    public void test_zc_90210() {

        //Response response = RestAssured.get("http://api.zippopotam.us/us/90210");

        //System.out.println(response.getBody().asString());
        //assertTrue(response.getBody().asString().contains("Beverly Hills"));
        //assertEquals(response.statusCode(),200);
        //assertEquals(response.contentType(), "application/json");
        //assertEquals(response.getBody().jsonPath().get("places[0].'place name'").toString(),"Beverly Hills");

    }

    @Test
    public void test_zc_90210_log() {
        //Logging response data
        var response = RestAssured.get("http://api.zippopotam.us/us/90210");
        response.then().log().all();
    }

    @Test
    public void test_zc_90210_expect_California() {
        given().
                spec(requestSpec).
                when().
                get("us/90210").
                then().
                spec(responseSpec).and().
                assertThat().
                body("places[0].'place name'",equalTo("Beverly Hills"));


    }

    @Test
    public void test_zc_90210_get_and_expect_California() {
        String placeName = given().
                spec(requestSpec).
                when().
                get("us/90210").
                then().
                extract().
                path("places[0].'place name'");
        assertEquals(placeName,"Beverly Hills");


    }
}

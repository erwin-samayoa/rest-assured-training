import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class Chapter2Test {
    @Test
    public void testZipCodeGives200OK() {

        given().
                when().
                get("http://api.zippopotam.us/us/90210").
                then().
                assertThat().
                contentType(ContentType.JSON).and().
                statusCode(200);

    }

    @Test
    public void getZipCodeLog() {

        given().
                log().all().
                when().

                get("http://api.zippopotam.us/us/90210").
                then().
                log().body();

    }

    @Test
    public void testZipCode() {

        given().
                when().
                get("http://api.zippopotam.us/us/90210").
                then().
                assertThat().
                body("places[0].'place name'",equalTo("Beverly Hills"));

    }

    @Test
    public void testZipCodeContains() {

        given().
                when().
                get("http://api.zippopotam.us/us/90210").
                then().
                assertThat().
                body("places.'place name'",hasItem("Beverly Hills"));

    }
}

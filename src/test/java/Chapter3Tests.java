import io.restassured.RestAssured;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class Chapter3Tests {
    @DataProvider (name = "data-provider-zc")
    public Object[][] dataProviderMethod() {
        return new Object[][] {
                { "us","90210","Beverly Hills"},
                { "us", "12345", "Schenectady"},
                { "ca", "B2R", "Waverley"}
        };
    }

    @Test (dataProvider = "data-provider-zc")
    public void testZipCodes(String countryCode, String zipCode, String expectedPlaceName) {
        System.out.println("Testing: " + countryCode + " " + zipCode + " to be " + expectedPlaceName);
        var response = RestAssured.get("http://api.zippopotam.us/" + countryCode + "/" + zipCode);

        assertEquals(response.getBody().jsonPath().get("places[0].'place name'"),expectedPlaceName);
    }
}

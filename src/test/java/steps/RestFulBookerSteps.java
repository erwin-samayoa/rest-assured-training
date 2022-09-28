package steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import utils.RestAssuredExtension;


import java.util.HashMap;


import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class RestFulBookerSteps {

    RestAssuredExtension restAssuredExtension;



    /*
    @io.cucumber.java.en.Given("^I perform post operation for \"([^\"]*)\" using \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iPerformPostOperationForUsingAnd(String endpoint, String user, String password) throws Throwable {
        //Old Restassured ghertin test (missing request and response Specs)

        //"https://restful-booker.herokuapp.com"

        JSONObject postContent = new JSONObject();
        postContent.put("username",user);
        postContent.put("password",password);

        //Alternative:

        //String bodyString = "{\n" +
        //                        "    \"username\" : \"admin\",\n" +
        //                        "    \"password\" : \"password123\"\n" +
        //                        "}";




        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(postContent.toString())
                //.param("username",user)
                //.param("password",password)

                .when()
                .post(endpoint)

                .then()
                .spec(responseSpec)

                .body("", hasKey("token"));


    }

     */


    @Given("I want to perform an API call")
    public void iWantToPerformAnAPICall() {
        restAssuredExtension = new RestAssuredExtension("https://restful-booker.herokuapp.com");
    }


    @When("I perform post operation on {string} using {string} and {string}")
    public void iPerformPostOperationUsingAnd(String endpoint, String user, String password) {
        JSONObject postContent = new JSONObject();
        postContent.put("username",user);
        postContent.put("password",password);

        restAssuredExtension.postJson(endpoint,postContent);
    }

    @io.cucumber.java.en.Then("^I should get a token$")
    public void iShouldGetAToken() {
        assertThat(restAssuredExtension.getBodyAsString(),containsString("token"));
    }
}

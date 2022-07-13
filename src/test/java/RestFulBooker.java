import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class RestFulBooker {

    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    private Integer bookingId = 0;
    private AuthToken token;

    //POJO classes
    class Credentials {
        private String username = "";
        private String password = "";

        public Credentials(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

    }

    /*
    public class BookingDates {
        private String checkIn;
        private String checkOut;
    }

     */

    public class Booking {
        private Integer id;
        private String firstName;
        private String lastName;
        private Integer totalPrice;
        private Boolean depositPaid;
        //public BookingDates bookingDates;
        private HashMap<String, String> bookingDates;
        private String additionalNeeds;

        public Booking() {
            this.id = 0;
            //bookingDates = new BookingDates();
        }

        public void setBookingId(Integer id) {
            this.id = id;
        }

        public void setBookingDates(HashMap<String, String> bookingDates) {
            this.bookingDates = bookingDates;
        }

        public HashMap<String, String> getbookingdates() {
            return bookingDates;
        }

        public String getadditionalneeds() {
            return additionalNeeds;
        }

        public void setAdditionalNeeds(String additionalNeeds) {
            this.additionalNeeds = additionalNeeds;
        }

        public String getfirstname() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getlastname() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Integer gettotalprice() {
            return totalPrice;
        }

        public void setTotalPrice(Integer totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Boolean getdepositpaid() {
            return depositPaid;
        }

        public void setDepositPaid(Boolean depositPaid) {
            this.depositPaid = depositPaid;
        }



    }

    public static class AuthToken {
        String token;

        AuthToken() {
            token = "";
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }

    @BeforeSuite
    private void setUp() {
        requestSpec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build();
        responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200).build();
    }

    @Test
    public void testLogin() {

        //Test loging with POST data in hashmap

        HashMap<String,String> postContent = new HashMap<>();
        postContent.put("username","admin");
        postContent.put("password","password123");

        given().
            spec(requestSpec).
            contentType(ContentType.JSON).
            body(postContent).
            //log().body().
        when().
            post("auth").
        then().
            spec(responseSpec).
            assertThat().
            body("",hasKey("token"));

    }

    //@BeforeClass //Commented just to avoid executing it twice
    @Test //Handled order with number inside name
    public void test1loginWithClass() {
        //Test loging with POST data in object serialization
        //and deserialization to object
        var credentials = new Credentials("admin", "password123"); //Avoids typos in field names
        token =
        given().
                spec(requestSpec).
                contentType(ContentType.JSON). //Very important when serializing
                body(credentials).
                //log().body().
        when().
                post("auth").
                as(AuthToken.class);
        assertFalse(token.getToken().isEmpty());

    }

    @Test
    public void testCreateBooking() {
        //Create with Object serialization and an extract
        Booking booking = new Booking();
        booking.setFirstName("Juan");
        booking.setLastName("Perez");
        booking.setTotalPrice(100);
        booking.setDepositPaid(false);
        //var bookingDates = new BookingDates();
        HashMap<String,String> bookingDates = new HashMap<>();
        bookingDates.put("checkin","2022-07-11");
        bookingDates.put("checkout","2022-07-11");
        booking.setBookingDates(bookingDates);
        //booking.bookingDates.setCheckIn("2022-07-11");
        //booking.bookingDates.setCheckOut("2022-07-11");

        //booking.setBookingDates(bookingDates);
        booking.setAdditionalNeeds("breakfast");

        bookingId = given().
            //log().all().
            spec(requestSpec).
            contentType(ContentType.JSON).
            body(booking).
        when().
            post("booking").
        then().
            extract().
            path("bookingid");
        assertTrue(bookingId > 0);
        //System.out.println(bookingId);
    }

    @Test
    public void testGetBookings() {
        Integer bookingIdToTest = 370;
        if (bookingId > 0) {
            bookingIdToTest = bookingId;
        }
        given().
                spec(requestSpec).
                //log().all().
        when().
                get("booking").
        then().
                //log().body().
                spec(responseSpec).
                        assertThat().
                body("bookingid",hasItem(bookingIdToTest));


    }

    @Test
    public void testGetBookingById() {
        Integer bookingIdToTest = 370;
        if (bookingId > 0) {
            bookingIdToTest = bookingId;
        }
        given().
                spec(requestSpec).
                //log().all().
        when().
                get("booking/" + bookingIdToTest ) .
        then().
                spec(responseSpec).
                //log().body().
                        assertThat().
                body("firstname",equalTo("Juan"));
    }

    @Test
    public void testUpdateBooking() {
        //Update using Auth cookie and serialization
        Booking booking = new Booking();
        booking.setFirstName("Juan2");
        booking.setLastName("Perez2");
        booking.setDepositPaid(false);
        booking.setAdditionalNeeds("none");
        booking.setTotalPrice(200);
        HashMap<String,String> bookingDates = new HashMap<>();
        bookingDates.put("checkin","2022/07/13");
        bookingDates.put("checkout","2022/07/13");
        booking.setBookingDates(bookingDates);

        given().
                //log().all().
                spec(requestSpec).
                cookie("token",token.getToken()).
                contentType(ContentType.JSON).
                body(booking).
        when().
                put("booking/" + bookingId).
        then().
                //log().all().
                spec(responseSpec);


    }

    @Test
    public void testPartialUpdate() {
        //Update using hashmap as parameters
        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("firstname","Juan3");
        parameters.put("lastname","Perez3");

        given().
                //log().all().
                spec(requestSpec).
                contentType(ContentType.JSON). //If this is not specified, the update doesnt work
                cookie("token",token.getToken()).

                body(parameters).
        when().
                patch("booking/" + bookingId).
        then().
                //log().all().
                spec(responseSpec);
        testGetBookingById(); //This will fail because the hardcoded Assert

    }

    @Test //Priority handlede with z in name
    public void testzDeleteBooking() {
        given().
                spec(requestSpec).
                cookie("token",token.getToken()).
        when().
                delete("booking/" + bookingId).
        then().
                assertThat().
                statusCode(201);
    }
}

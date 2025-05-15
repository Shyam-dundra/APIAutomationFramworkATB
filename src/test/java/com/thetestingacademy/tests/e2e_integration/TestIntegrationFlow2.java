package com.thetestingacademy.tests.e2e_integration;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import com.thetestingacademy.pojos.request.BookingResponse;
import com.thetestingacademy.pojos.response.Booking;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow2 extends BaseTest {

    //  Test E2E Scenario 2
    //  1. Create a Booking -> bookingID
    // 2. Delete the Booking - Need to get the token, bookingID from above request
    // 3. Verify that the Create Booking is working - GET Request to bookingID

    @Test(groups = "qa", priority = 1)
    @Owner("Shyam")
    @Description("TC#1 - Step 1. Verify that the Booking can be Created")
    public void testCreateBooking (ITestContext iTestContext){

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured
                .given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsString())
                .post();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "Shyam");
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());

        iTestContext.setAttribute("bookingid", bookingResponse.getBookingid());
    }


    @Test(groups = "qa", priority = 2)
    @Owner("Shyam")
    @Description("TC#2 - Step 2. Delete the Booking by ID")
    public void testDeleteBooking(ITestContext iTestContext){

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();

        iTestContext.setAttribute("token", token);

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);
    }


    @Test(groups = "qa", priority = 3)
    @Owner("Shyam")
    @Description("TC#INT1 - Step3 . Verify that the Booking By ID")
    public void testVerifyBookingID(ITestContext iTestContext){

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathGET);

        requestSpecification.basePath(basePathGET);
        response = RestAssured
                .given(requestSpecification)
                .when().get();
        validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(404);

    }


}

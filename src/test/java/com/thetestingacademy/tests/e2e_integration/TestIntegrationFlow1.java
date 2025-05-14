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

public class TestIntegrationFlow1 extends BaseTest {

    @Test(groups = "qa", priority = 1)
    @Owner("Shyam")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be Created")
    public void testCreateBooking(ITestContext iTestContext) {

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
    @Description("TC#INT1 - Step 2. Verify that the Booking By ID")
    public void testVerifyBookingId(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePath = APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid;

        requestSpecification.basePath(basePath);

        response = RestAssured.given(requestSpecification).when().get();
        validatableResponse=response.then().log().all();
        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertActions.verifyStringKeyNotNull(booking.getFirstname());
        assertActions.verifyStringKeyNotNull(bookingid);

    }

    @Test(groups = "qa", priority = 3)
    @Owner("Shyam")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")
    public void testUpdateBookingByID(ITestContext iTestContext){

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token",token);


        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathPUTPATCH);

        requestSpecification.basePath(basePathPUTPATCH);

        response = RestAssured
                .given(requestSpecification).cookie("token", token)
                .when().body(payloadManager.fullUpdatePayloadAsString()).put();


        validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());

        assertActions.verifyStringKeyNotNull(booking.getFirstname());
        assertActions.verifyStringKey(booking.getFirstname(),"Mohan");

    }

    @Test(groups = "qa", priority = 4)
    @Owner("Shyam")
    @Description("TC#INT1 - Step 4. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext){

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = (String)iTestContext.getAttribute("token");

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);


    }
}
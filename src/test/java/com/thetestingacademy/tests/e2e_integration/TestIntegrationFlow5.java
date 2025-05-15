package com.thetestingacademy.tests.e2e_integration;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import com.thetestingacademy.pojos.response.Booking;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow5 extends BaseTest {

    // TestE2EFlow_05
    //  Test E2E Scenario 5
    // 1. Delete the Booking - Need to get the token, bookingID from above request
    // 2. Update the booking ( bookingID, Token) - Need to get the token, bookingID from above request

    @Test(groups = "qa", priority = 1)
    @Owner("Shyam")
    @Description("TC#2 - Step 2. Delete the Booking by ID")
    public void testDeleteBookingID(ITestContext iTestContext){

        Integer bookingid = getBookingId();
        String token = getToken();
        iTestContext.setAttribute("token", token);

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);
        System.out.println(bookingid);
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Shyam")
    @Description("TC#INT1 - Step 3. Verify the Updated Booking after deleted by ID")
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
        validatableResponse.statusCode(405);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());

        assertActions.verifyStringKeyNotNull(booking.getFirstname());
        assertActions.verifyStringKey(booking.getFirstname(),"Mohan");

    }

}

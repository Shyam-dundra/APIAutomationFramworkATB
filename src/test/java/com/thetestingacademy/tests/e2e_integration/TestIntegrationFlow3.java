package com.thetestingacademy.tests.e2e_integration;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import org.testng.ITestContext;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class TestIntegrationFlow3 extends BaseTest {

    //TestE2EFlow_03
    //  Test E2E Scenario 3  -> Try to Delete that booking
    //  1. Get a Booking from Get All -> bookingID
    // 2. Delete the Booking - Need to get the token, bookingID from above request


    @Test(groups = "QA", priority = 1)
    @Owner("Shyam")
    @Description("TC#1- Step 1. Verify that the Booking By ID")
    public void testVerifyGetBookingIDs (ITestContext iTestContext){

       Integer bookingid = getBookingId();

        iTestContext.setAttribute("bookingid", bookingid );
        validatableResponse=response.then().log().all();
        validatableResponse.statusCode(200);
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Shyam")
    @Description("TC#2 - Step 2. Delete the Booking by ID")
    public void testDeleteBookingID(ITestContext iTestContext){

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();

        iTestContext.setAttribute("token", token);

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);
    }


}

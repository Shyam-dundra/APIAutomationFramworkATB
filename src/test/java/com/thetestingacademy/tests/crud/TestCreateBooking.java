package com.thetestingacademy.tests.crud;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import com.thetestingacademy.modules.PayloadManager;
import com.thetestingacademy.pojos.request.BookingResponse;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.requestSpecification;

public class TestCreateBooking extends BaseTest {

    @Test
    public void testCreateBookingPOST_Positive() {

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsString()).post();

        validatableResponse  = response.then().log().all();
        validatableResponse.statusCode(200);


        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Shyam");

    }

    @Test
    public void testCreateBookingPOST_POSITIVE_CHINESE() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsString()).log().all().post();
        System.out.println(response.asString());

        validatableResponse  = response.then().log().all();
        validatableResponse.statusCode(200);

    }


}

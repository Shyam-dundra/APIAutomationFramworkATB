package com.thetestingacademy.tests.crud;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import com.thetestingacademy.modules.PayloadManager;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class TestHealthCheck extends BaseTest {

    @Owner("Shyam")
    @Test
    public void testHealthCheck (){
        requestSpecification.basePath(APIConstants.PING_URL);
        response= RestAssured.given(requestSpecification).when().get();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);
    }
}

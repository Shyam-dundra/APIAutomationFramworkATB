package com.thetestingacademy.tests.crud;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import com.thetestingacademy.modules.PayloadManager;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class TestCreateToken extends BaseTest {

    @Owner("Shyam")
    @Test
    public void testTokenPOST (){
        requestSpecification.basePath(APIConstants.AUTH_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.setAuthPayload()).post();

        String token = payloadManager.getTokenFromJSON(response.asString());
        System.out.println(token);

        assertActions.verifyStringKeyNotNull(token);

    }


}

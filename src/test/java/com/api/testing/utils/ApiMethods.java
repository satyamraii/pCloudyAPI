package com.api.testing.utils;

import org.testng.asserts.SoftAssert;


import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;

public class ApiMethods {
	
	public static String executeApiCall(String method, String username, String apiKey, String headerToken, String jsonBody, String url, File fileToUpload) {
	    Response response;

	    // ✅ GET with basic auth
	    if ("GET".equalsIgnoreCase(method) && username != null && apiKey != null) {
	        response = RestAssured.given()
	                .auth().preemptive().basic(username, apiKey)
	                .when()
	                .get(url)
	                .then()
	                .extract()
	                .response();

	    }
	    // ✅ GET with token in header
	    else if ("GET".equalsIgnoreCase(method) && headerToken != null) {
	        response = RestAssured.given()
	                .header("token", headerToken)
	                .when()
	                .get(url)
	                .then()
	                .extract()
	                .response();

	    }
	    // ✅ GET without auth
	    else if ("GET".equalsIgnoreCase(method)) {
	        response = RestAssured.given()
	                .when()
	                .get(url)
	                .then()
	                .extract()
	                .response();

	    }
	    // ✅ POST with file
	    else if (fileToUpload != null) {
	        response = RestAssured.given()
	                .header("token", headerToken)
	                .multiPart("file", fileToUpload)
	                .when()
	                .post(url)
	                .then()
	                .extract()
	                .response();

	    }
	    // ✅ POST with token
	    else if (headerToken != null) {
	        response = RestAssured.given()
	                .header("Authorization", "Bearer ")
	                .header("token", headerToken)
	                .contentType("application/json")
	                .body(jsonBody)
	                .when()
	                .post(url)
	                .then()
	                .extract()
	                .response();

	    }
	    // ✅ Fallback default POST
	    else {
	        response = RestAssured.given()
	                .header("Authorization", "Bearer ")
	                .contentType("application/json")
	                .body(jsonBody)
	                .when()
	                .post(url)
	                .then()
	                .extract()
	                .response();
	    }

	    return response.getBody().asString();
	}
}
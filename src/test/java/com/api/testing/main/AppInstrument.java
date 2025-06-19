package com.api.testing.main;

import org.testng.annotations.Test;

import com.api.testing.utils.ApiEndPoints;
import com.api.testing.utils.ApiMethods;

public class AppInstrument {

	public static String token;
	
	@Test (priority = 1)
	public static void getAuthToken() {
	    
		String userName = "satyam.kumar@sstsinc.com";
		String apiKey = "zq76hq3yt7v2stmzshzqp8xf";

	    
		String response = ApiMethods.executeApiCall("GET", userName, apiKey, null, null, ApiEndPoints.ACCESS_TOKEN, null);
		token = response.split("\"token\":\"")[1].split("\"")[0];
		System.out.println("Token: " + token);
   }
	
	
}

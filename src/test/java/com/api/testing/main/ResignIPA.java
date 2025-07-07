package com.api.testing.main;

import java.util.Map;

import org.testng.annotations.Test;

import com.api.testing.utils.ApiEndPoints;
import com.api.testing.utils.ApiMethods;
import com.api.testing.utils.JsonPayload;
import com.api.testing.utils.JsonUtil;
import com.api.testing.utils.SearchInstallAPP;

import io.restassured.path.json.JsonPath;

public class ResignIPA {
	
	public static String token;
	public static String targetFileName;
	public static String resignToken;
	public static String resignFileName;
	
	@Test(priority = 1)
	public static void getAuthToken() {

		String userName = "satyam.kumar@sstsinc.com";
		String apiKey = "hmww27d3dj44rs4zmxbx8vhf";

		// Get access token
		String response = ApiMethods.executeApiCall("GET", userName, apiKey, null, null, ApiEndPoints.ACCESS_TOKEN, null);
		token = response.split("\"token\":\"")[1].split("\"")[0];
		System.out.println("Token: " + token);

	}
	
	@Test(priority = 2)
	public static void getAvailableApps() {
		//App Drive
		String apkDrive = JsonPayload.appDrive(token);
		JsonPath listOfApps = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, apkDrive, ApiEndPoints.DRIVE, null));
	//	System.out.println(listOfApps);

		// Extract the installed application from APK Driver response
		targetFileName = "MiniSafari-1751874223.ipa";

		// Find the file from JSON
		Map<String, Object> matchedFile = SearchInstallAPP.findFileByName(listOfApps, targetFileName);

		// Print or use it
		SearchInstallAPP.printFileDetails(matchedFile);
	}

	@Test(priority = 3)
	public static void initiateResign() {
		
		String resigningInitiate = JsonPayload.resignInitiate(token, targetFileName);
		System.out.println("Request response is "+ resigningInitiate);
		JsonPath resignStart = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, resigningInitiate, ApiEndPoints.INITIATE_RESIGN, null));
	    System.out.println("Resigning Initiate");
	    
	   resignToken = resignStart.getString("result.resign_token");
	   System.out.println("ResignToken is : " + resignToken);
	   
	   resignFileName= resignStart.getString("result.resign_filename");
	   System.out.println("ResignFileName is: " + resignFileName);
	  
	   
	}
	
	@Test(priority = 4)
	public static void resignProgress() {
		
		String ipaResign = JsonPayload.resignProgress(token, resignToken, resignFileName);
		System.out.println("Request response is "+ ipaResign);
		JsonPath resignInProgress = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, ipaResign, ApiEndPoints.RESIGN_PROGRESS, null));
		System.out.println("IPA Resigning in Progress");
	}
	
	@Test(priority = 5)
	public static void resignDownload() {
		
		String downloadResignIPA = JsonPayload.resignDownload(token, resignToken, resignFileName);
		System.out.println("Request response is "+ downloadResignIPA);
		JsonPath resignedIPA = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, downloadResignIPA, ApiEndPoints.RESIGN_DOWNLOAD, null));
		System.out.println("IPA has been resigned");
		
	}
	
	
	
	
}

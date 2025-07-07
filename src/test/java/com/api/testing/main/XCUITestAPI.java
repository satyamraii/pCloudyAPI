package com.api.testing.main;

import java.util.Map;

import org.testng.annotations.Test;

import com.api.testing.utils.ApiEndPoints;
import com.api.testing.utils.ApiMethods;
import com.api.testing.utils.JsonPayload;
import com.api.testing.utils.JsonUtil;
import com.api.testing.utils.SearchInstallAPP;

import io.restassured.path.json.JsonPath;

public class XCUITestAPI {
	
	public static String token;
	public static int deviceId;
	public static int automationId;
	public static String targetFileName;
	
	@Test (priority = 1)
	public static void getAuthToken() {
	    
		String userName = "satyam.kumar@sstsinc.com";
		String apiKey = "jpyb6557kvsbgmj82gw82vng";

	    
		String response = ApiMethods.executeApiCall("GET", userName, apiKey, null, null, ApiEndPoints.ACCESS_TOKEN, null);
		token = response.split("\"token\":\"")[1].split("\"")[0];
		System.out.println("Token: " + token);
	}

	
	@Test (priority = 2)
	public static void getDeviceList() {
		
		String deviceListBody = JsonPayload.getDeviceListRequest(token);
        System.out.println(deviceListBody);
        
		JsonPath deviceListJson = JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, null, deviceListBody, ApiEndPoints.DEVICE_LIST, null));
		
		deviceId = deviceListJson.getInt("result.models[0].id");
		System.out.println("Device ID obtained: " + deviceId);
		
	}
	
	@Test (priority = 3)
	public static void bookDevice() {
		
		String bookDeviceBody = JsonPayload.xcuiTestDeviceBooking(token,deviceId);
        System.out.println(bookDeviceBody);
        
        
		JsonPath bookDeviceXCTest= JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, null, bookDeviceBody, ApiEndPoints.BOOK_DEVICE_XCTEST, null));
		
		automationId= bookDeviceXCTest.getInt("data.automationId");
		System.out.println("Automation ID: " + automationId);
		
	}
	
	@Test(priority = 4)
	public static void getAvailableApps() {
		//App Drive
		String apkDrive = JsonPayload.appDrive(token);
		JsonPath listOfApps = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, apkDrive, ApiEndPoints.DRIVE, null));
	//	System.out.println(listOfApps);

		// Extract the installed application from APK Driver response
		targetFileName = "TestingArchivev2.zip";

		// Find the file from JSON
		Map<String, Object> matchedFile = SearchInstallAPP.findFileByName(listOfApps, targetFileName);

		// Print or use it
		SearchInstallAPP.printFileDetails(matchedFile);

	}
	
	@Test (priority = 5)
	public static void startXCTestAutomation() {
		
		String startAutomation = JsonPayload.xcTestINIT(automationId, targetFileName);
		System.out.println(startAutomation);
		
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, startAutomation, ApiEndPoints.XCTEST_INITV2, null));
		System.out.println("Execution has been started");
		
	}
	
	@Test (priority = 6)
	public static void xcTestStartDeviceService() {
		
		String startDeviceService = JsonPayload.xcTestStartDeviceService(token, automationId);
		System.out.println(startDeviceService);
		
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, startDeviceService, ApiEndPoints.START_DEVICESERVICEXCTEST, null));
		System.out.println("StartDeviceService has been enabled");
	}
	
	@Test (priority = 7)
	public static void releaseXCTestDevice() {
		
		String releaseDevice = JsonPayload.releaseXCTestDevice(token, automationId);
		System.out.println(releaseDevice);
		
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, releaseDevice, ApiEndPoints.RELEASE_XCTESTDEVICE, null));
		System.out.println("Device has been released successfully");
	}
	
	@Test (priority = 8)
	public static void xcTestShareableReportLink() {
		
		String shareableReportLink= JsonPayload.xcTestShareableReportLink(token, automationId);
		System.out.println(shareableReportLink);
		
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, shareableReportLink, ApiEndPoints.XCTESTSHREABLE_REPORTLINK, null));
		System.out.println("Here is the shareable report link");
	}
	


}

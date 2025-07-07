package com.api.testing.main;

import java.util.Map;

import org.testng.annotations.Test;

import com.api.testing.utils.ApiEndPoints;
import com.api.testing.utils.ApiMethods;
import com.api.testing.utils.JsonPayload;
import com.api.testing.utils.JsonUtil;
import com.api.testing.utils.SearchInstallAPP;

import io.restassured.path.json.JsonPath;

public class BiometricAppInstrument {

	public static String token;
	public static String targetFileName;
	public static String instrumentationToken;
	public static String deviceId;
	public static int rid;
	
	@Test (priority = 1)
	public static void getAuthToken() {
	    
		String userName = "satyam.kumar@sstsinc.com";
		String apiKey = "hmww27d3dj44rs4zmxbx8vhf";
	    
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
		targetFileName = "Biometric_Prompt_app.apk";

		// Find the file from JSON
		Map<String, Object> matchedFile = SearchInstallAPP.findFileByName(listOfApps, targetFileName);

		// Print or use it
		SearchInstallAPP.printFileDetails(matchedFile);

	}
	
	@Test(priority = 3)
	public static void initiateInstrumentationBiometric() {
		
		String apkInsrument= JsonPayload.initiateInstrumentationBiometric(token,targetFileName);
		JsonPath initiateInstrumentation = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, apkInsrument, ApiEndPoints.INITIATE_APP_INSTRUMENTATION, null));
		System.out.println("App Instrumentation has been started");
		
		 // Extract instrumentation_token from response
	    instrumentationToken = initiateInstrumentation.getString("result.instrumentation_token");
	    
	    System.out.println("InstrumentationToken: " + instrumentationToken);
	    System.out.println("App Instrumentation has been started");
	}
	
	@Test(priority = 4, invocationCount = 3)
	public static void AppInstrumentProgress() throws InterruptedException {
		
		if (instrumentationToken == null || instrumentationToken.isEmpty()) {
	        System.err.println("❌ instrumentationToken is null or empty. Please check the initiation step.");
	        return;
	    }
		
		String instrumentationProgress = JsonPayload.instrumentationProgress(token, instrumentationToken);
		System.out.println("Request response is "+ instrumentationProgress);
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, instrumentationProgress, ApiEndPoints.INSTRUMENTATION_PROGRESS, null));
		Thread.sleep(10000);
		
		System.out.println("App Instrumentation In Progress");
	}
	
	@Test(priority = 5)
	public static void downloadInstrumentAPK() {
		
		String downloadInstrumentedAPK= JsonPayload.downloadInstrumentAPK(token, instrumentationToken);
		System.out.println("Request response is "+ downloadInstrumentedAPK);
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, downloadInstrumentedAPK, ApiEndPoints.DOWNLOAD_INSTRUMENTATION_APK, null));
		System.out.println("App Instrumentation done");
		
		
	}
	
	@Test(priority = 6)
	public static void getDeviceList() {
		
		String deviceListBody = JsonPayload.getDeviceListRequest(token);
		JsonPath deviceListJson = JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, null, deviceListBody, ApiEndPoints.DEVICE_LIST, null));
		
	    deviceId = deviceListJson.getString("result.models[0].id");
		System.out.println("Device ID obtained: " + deviceId);
		
	}
	
	@Test(priority = 7)
	public static void bookDevice() {
		
		String bookDevice= JsonPayload.bookDeviceRequest(token, deviceId);
		System.out.println("Request response is "+ bookDevice);
		
		JsonPath bookDeviceJson = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null,
				bookDevice, ApiEndPoints.BOOK_DEVICE, null));
		System.out.println("Device has been booked");

		rid = bookDeviceJson.getInt("result.rid");
		System.out.println("Device booked with RID: " + rid);

	}

	@Test(priority = 8)
	public static void getAppsList() {

		String apkDrive = JsonPayload.appDrive(token);
		JsonPath listOfApps = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, apkDrive, ApiEndPoints.DRIVE, null));
		Map<String, Object> matchedFile = SearchInstallAPP.findFileByName(listOfApps, targetFileName);
		SearchInstallAPP.printFileDetails(matchedFile);

	}
	
	@Test(priority = 9)
	public static void fetchAvailableApps() {
		//App Drive
		String apkDrive = JsonPayload.appDrive(token);
		JsonPath listOfApps = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, apkDrive, ApiEndPoints.DRIVE, null));
	//	System.out.println(listOfApps);

		// Extract the installed application from APK Driver response
		targetFileName = "Biometric_Prompt_app_BAuth1751031035.apk";

		// Find the file from JSON
		Map<String, Object> matchedFile = SearchInstallAPP.findFileByName(listOfApps, targetFileName);

		// Print or use it
		SearchInstallAPP.printFileDetails(matchedFile);

	}
	
	@Test(priority = 10)
	public static void installInstrumentedAPP() {
		
		String installApplication = JsonPayload.installApp(token, rid, targetFileName);
		System.out.println("Request response is "+ installApplication);
		
	JsonPath installApp = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null,
			installApplication, ApiEndPoints.INSTALL_APP, null));
	System.out.println("App installed successfully.");
	
	}
	
	@Test(priority = 11, invocationCount = 3)
	public static void fingerprintAuth()
	{
		String BioAuth= JsonPayload.biometricAuth(token, rid);
		System.out.println("Request response is "+ BioAuth);
		
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null,BioAuth, ApiEndPoints.BIOMETRIC_AUTHENTICATION, null));
		System.out.println("FingerPrint Auth done");
		
	}
	
	@Test(priority = 12)
	public static void releaseDevice() {


		String releaseDevice= JsonPayload.releaseDevice(token, rid);
		System.out.println("Request response is: "+ releaseDevice);
		JsonPath deviceRelease= JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, releaseDevice, ApiEndPoints.RELEASE_DEVICE, null));
		System.out.println("Device has been released");
	}
	
}

package com.api.testing.main;

import java.util.Map;

import org.testng.annotations.Test;

import com.api.testing.utils.ApiEndPoints;
import com.api.testing.utils.ApiMethods;
import com.api.testing.utils.JsonPayload;
import com.api.testing.utils.JsonUtil;
import com.api.testing.utils.SearchInstallAPP;

import io.restassured.internal.mapping.JsonbMapper;
import io.restassured.path.json.JsonPath;

public class AppiumAutomationAPI {

	public static String token;
	public static int deviceId;
	public static int rid;
	public static String targetFileName;
	public static String getFileName;

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
	public static void getUserInfo() {

		//Get userInfo
		String userInfo= JsonPayload.getUserInfo();
		System.out.println("Request response is: "+ userInfo);
		JsonPath getUserDetails= JsonUtil.printFormattedJson(ApiMethods.executeApiCall("GET", null, null, token, userInfo, ApiEndPoints.USER_INFO, null));

	}
	
	@Test(priority = 3)
	public static void getAvailableDeviceList() {
		// Get device list and first deviceId
		String deviceListBody = JsonPayload.getDeviceListRequest(token);
		JsonPath deviceListJson = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, deviceListBody, ApiEndPoints.DEVICE_LIST, null));
		
		deviceId = deviceListJson.getInt("result.models[0].id");
		System.out.println("📱 Device ID: " + deviceId);
		//deviceId = deviceListJson.getInt("result.models[0].id");
	}

	//		//init appium(v2)
	//		String initV2= JsonPayload.appiumInitV2(deviceId);
	//		System.out.println("Request response is: "+ initV2);
	//		JsonPath appiumInitV2= JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, initV2, ApiEndPoints.APPIUM_INITV2, null));
	
	@Test(priority = 4)
	public static void bookDeviceForAppium() {
		//init appium
		String initAppium = JsonPayload.appiumInit(token, deviceId);
		System.out.println("Request response is: "+ initAppium);
		JsonPath bookDeviceAppium= JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, initAppium, ApiEndPoints.APPIUM_INIT, null));

		// Assign to class-level variable
		rid = bookDeviceAppium.getInt("result.device_ids[0].rid");
		System.out.println("🆔 RID: " + rid);
	}
	
	@Test(priority = 5)
	public static void startStreamer(){		
		//start-streamer
		String streamer = JsonPayload.initLiveView(rid);
		System.out.println("Request response is: "+ streamer);
		JsonPath liveViewStreamer = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, streamer, ApiEndPoints.INIT_LIVEVIEW, null));
	}
	
	@Test(priority = 6)
	public static void unlockIOSDevice() {
		//Unlock device IOS
		String unlockIOS= JsonPayload.unlockDeviceIOS(rid);
		System.out.println("Request response is: "+ unlockIOS);
		JsonPath unlockIOSDevice = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, unlockIOS, ApiEndPoints.UNLOCK_DEVICE, null));
	}	

	@Test(priority = 7)
	public static void getAvailableApps() {
		//App Drive
		String apkDrive = JsonPayload.appDrive(token);
		JsonPath listOfApps = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, apkDrive, ApiEndPoints.DRIVE, null));
	//	System.out.println(listOfApps);

		// Extract the installed application from APK Driver response
		targetFileName = "TestmunkDemo-1683608873_Resigned1716649478.ipa";

		// Find the file from JSON
		Map<String, Object> matchedFile = SearchInstallAPP.findFileByName(listOfApps, targetFileName);

		// Print or use it
		SearchInstallAPP.printFileDetails(matchedFile);

	}
	
	@Test(priority = 8)
	public static void installApp() {
		//Install APP
		String installAPK = JsonPayload.installApp(token, rid, targetFileName);
		JsonPath installApp= JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, installAPK, ApiEndPoints.INSTALL_APP, null));
		System.out.println("App has been installed");
	}
	
	@Test(priority = 9)
	public static void startAutomation() {
		//Lonely-Appium
		String startAutomation = JsonPayload.lonelyAppium(token);
		System.out.println("Request response is :"+ startAutomation);
		JsonPath lonelyAppium= JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, startAutomation, ApiEndPoints.LONELY_APPIUM, null));
	}
	
	@Test(priority = 10)
	public static void captureDeviceScreenshot() {
		//Capturedevice screenshot
		String captureScreenshot = JsonPayload.captureDeviceScreenshot(token, rid);
		System.out.println("Request response is :"+ captureScreenshot );
		JsonPath screenshot= JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, captureScreenshot, ApiEndPoints.CAPTURE_SCREENSHOT, null));
	}

	@Test(priority = 11)
	public static void getPlayerType() {
		String getPlayerType = JsonPayload.getPlayerV2(rid);
		System.out.println("Request response is :" + getPlayerType);
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, getPlayerType, ApiEndPoints.GET_PLAYER, null));
	}
	
	@Test(priority = 12)
	public static void startDeviceService() {
		String enableDeviceService = JsonPayload.startDeviceService(token, rid);
		System.out.println("Request response is :" + enableDeviceService);
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, enableDeviceService, ApiEndPoints.START_DEVICESERVICE, null));
	}
	
//	@Test(priority = 13)
//	public static void getAppiumFileName() {
//		String getAppiumFileList= JsonPayload.getAppiumFileList(token, rid);
//		System.out.println("Request response is :" + getAppiumFileList);
//		
//		JsonPath getAppiumFileName= JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, getAppiumFileList, ApiEndPoints.GET_APPIUMFILELIST, null));
//		getFileName= getAppiumFileName.getString("result.res[1].file");
//		System.out.println("fileName is :"+ getFileName);
//	}
//	
//	@Test(priority = 14)
//	public static void downloadAppiumPerformanceData() {
//		
//		String requestBody = JsonPayload.downloadAppiumData(token, rid, getFileName);
//		System.out.println("Request response is: " + requestBody);
//		String rawResponse = ApiMethods.executeApiCall("POST", null, null, null, requestBody, ApiEndPoints.DOWNLOAD_APPIUMACESSDATA, null);
//		System.out.println("Raw response:\n" + rawResponse);
//	}
	
	@Test(priority = 15)
	public static void getAppiumEndpoint() {
		String getEndPoint = JsonPayload.endpointURL(token);
		System.out.println("Request response is: " + getEndPoint);
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, getEndPoint, ApiEndPoints.APPIUM_ENDPOINT, null));
	}
	
	@Test(priority = 16)
	public static void getAppiumReportFolder() {
		String getReportFolder = JsonPayload.getAppiumReportFolder(token);
		System.out.println("Request response is: " + getReportFolder);
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, getReportFolder, ApiEndPoints.APPIUM_FOLDER, null));
	}
	
	@Test(priority = 17)
	public static void getShareableReportLink() {
		String shareableReportLink = JsonPayload.shareableReportLink(token, rid);
		System.out.println("Request response is: " + shareableReportLink);
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, shareableReportLink, ApiEndPoints.GET_SHAREABLEREPORTLINK, null));
	}
	
	@Test(priority = 18)
	public static void downloadManualAccessData() {
		String manualAccessData = JsonPayload.downloadManualAccessData(token, rid);
		System.out.println("Request response is: " + manualAccessData);
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, manualAccessData, ApiEndPoints.DOWNLOAD_MANUALACCESSDATA, null));
	}
	
	@Test(priority = 19)
	public static void downloadFile() {
		String downloadFile = JsonPayload.downloadFile(token);
		System.out.println("Request response is: " + downloadFile);
		String raw = ApiMethods.executeApiCall("POST", null, null, null, downloadFile, ApiEndPoints.DOWNLOAD_FILE, null);
		System.out.println("Raw response:\n" + raw);
	}
	
	@Test(priority = 20)
	public static void getAvailableAppiumVersion() {
		String getAppiumVersion = JsonPayload.getAvailableAppiumVersion(token);
		System.out.println("Request response is: " + getAppiumVersion);
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, getAppiumVersion, ApiEndPoints.GET_APPIUMVERSION, null));
	}

	@Test(priority = 21)
	public static void releaseAppiumDevice() {
		String releaseDevice = JsonPayload.releaseAppiumDevice(token, rid);
		System.out.println("Request response is: " + releaseDevice);
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, releaseDevice, ApiEndPoints.RELEASE_DEVICEAUTOMATION, null));
	}
}
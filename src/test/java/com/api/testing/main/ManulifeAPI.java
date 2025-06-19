package com.api.testing.main;

import java.util.Map;

import org.testng.annotations.Test;

import com.api.testing.utils.*;

import io.restassured.path.json.JsonPath;

public class ManulifeAPI {

	@Test
	public void runManulifeAPI() {
		String userName = "satyam.kumar@sstsinc.com";
		String apiKey = "hmww27d3dj44rs4zmxbx8vhf";
		String token = null;
		int rid = -1;
		String targetFileName = "TestmunkDemo-1683608873_Resigned1716649478.ipa";

		try {
			// Get Access Token
			String response = ApiMethods.executeApiCall("GET", userName, apiKey, null, null, ApiEndPoints.ACCESS_TOKEN, null);
			token = response.split("\"token\":\"")[1].split("\"")[0];
			System.out.println("Access token obtained successfully.");

			// Get Device List
			String deviceListBody = JsonPayload.getDeviceListRequest(token);
			JsonPath deviceListJson = JsonUtil.printFormattedJson(
					ApiMethods.executeApiCall("POST", null, null, null, deviceListBody, ApiEndPoints.DEVICE_LIST, null));
			String deviceId = deviceListJson.getString("result.models[0].id");
			System.out.println("Device ID obtained: " + deviceId);

			// Get Device Details
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null,
					JsonPayload.getDeviceDetailsRequest(token, deviceId), ApiEndPoints.GET_DEVICES_DETAILS, null));

			// Book Device
			JsonPath bookDeviceJson = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null,
					JsonPayload.bookDeviceRequest(token, deviceId), ApiEndPoints.BOOK_DEVICE, null));
			rid = bookDeviceJson.getInt("result.rid");
			System.out.println("Device booked with RID: " + rid);

			// List APKs
			String apkDrive = JsonPayload.appDrive(token);
			JsonPath listOfApps = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, apkDrive, ApiEndPoints.DRIVE, null));
			Map<String, Object> matchedFile = SearchInstallAPP.findFileByName(listOfApps, targetFileName);
			SearchInstallAPP.printFileDetails(matchedFile);

			// Install App
			JsonPath installApp = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null,
					JsonPayload.installApp(token, rid, targetFileName), ApiEndPoints.INSTALL_APP, null));
			System.out.println("App installed successfully.");

			// Close & Open App (V1 & V2)
			executeAndLog("Closing app (V1)", JsonPayload.closeApp(token, rid), ApiEndPoints.CLOSE_APP);
			executeAndLog("Opening app (V1)", JsonPayload.openApp(token, rid, targetFileName), ApiEndPoints.OPEN_APP);
			executeAndLog("Closing app (V2)", JsonPayload.closeAppv2(rid), ApiEndPoints.CLOSE_APPV2, token);
			executeAndLog("Opening app (V2)", JsonPayload.openAppv2(rid, targetFileName), ApiEndPoints.OPEN_APPV2, token);

			// Send Text (V1 & V2)
			executeAndLog("Sending text (V1)", JsonPayload.sendText(token, rid), ApiEndPoints.SEND_TEXT);
			executeAndLog("Sending text (V2)", JsonPayload.sendTextV2(rid), ApiEndPoints.SEND_TEXTV2, token);

			// Rotate Device (V1 & V2)
			executeAndLog("Rotating device (V1)", JsonPayload.roateDevice(token, rid), ApiEndPoints.ROTATE_DEVICE);
			executeAndLog("Rotating device (V2)", JsonPayload.roateDeviceV2(rid), ApiEndPoints.ROTATE_DEVICEV2, token);

			// Tap on Device
			executeAndLog("Tapping on screen", JsonPayload.tap(token, rid), ApiEndPoints.TAP);

			// Release Device
			executeAndLog("Releasing device", JsonPayload.releaseDevice(token, rid), ApiEndPoints.RELEASE_DEVICE);

		} catch (Exception e) {
			System.err.println("Error during execution: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void executeAndLog(String action, String payload, String endpoint) {
		try {
			System.out.println(action + " request: " + payload);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, payload, endpoint, null));
			System.out.println(action + " completed successfully.");
		} catch (Exception e) {
			System.err.println("Failed during " + action + ": " + e.getMessage());
		}
	}

	private void executeAndLog(String action, String payload, String endpoint, String token) {
		try {
			System.out.println(action + " request: " + payload);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, payload, endpoint, null));
			System.out.println(action + " completed successfully.");
		} catch (Exception e) {
			System.err.println("Failed during " + action + ": " + e.getMessage());
		}
	}
}

package com.api.testing.main;

import java.util.Map;

import org.testng.annotations.Test;

import com.api.testing.utils.*;

import io.restassured.path.json.JsonPath;

public class ManulifeAPI {

	private String userName = "satyam.kumar@sstsinc.com";
	private String apiKey = "zq76hq3yt7v2stmzshzqp8xf";
	private String targetFileName = "TestmunkDemo_Resigned1754402851.ipa";

	@Test
	public void runManulifeAPI() {
		String token = null;
		int rid = -1;
		String deviceId = null;

		try {
			// 1. Get Access Token
			token = getAccessToken();
			if (token == null) {
				System.err.println("Failed to obtain access token. Aborting further operations.");
				return;
			}

			// 2. Get Device List
			deviceId = getDeviceList(token);
			if (deviceId == null) {
				System.err.println("Failed to obtain device ID. Aborting further operations.");
				return;
			}

			// 3. Get Device Details
			getDeviceDetails(token, deviceId);

			// 4. Book Device
			rid = bookDevice(token, deviceId);
			if (rid == -1) {
				System.err.println("Failed to book device. Aborting further operations.");
				return;
			}

			// 5. List APKs
			listAPKs(token);

			// 6. Install App
			installApp(token, rid);

			// 7. Close & Open App (V1 & V2)
			closeAppV1(token, rid);
			openAppV1(token, rid);
			closeAppV2(token, rid);
			openAppV2(token, rid);

			// 8. Send Text (V1 & V2)
			sendTextV1(token, rid);
			sendTextV2(token, rid);

			// 9. Rotate Device (V1 & V2)
			rotateDeviceV1(token, rid);
			rotateDeviceV2(token, rid);

			// 10. Tap on Device
			tapOnDevice(token, rid);

			// 11. Release Device
			releaseDevice(token, rid);

		} catch (Exception e) {
			System.err.println("An unexpected error occurred during API execution: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves the access token from the API.
	 * @return The access token string, or null if an error occurs.
	 */
	private String getAccessToken() {
		try {
			String response = ApiMethods.executeApiCall("GET", userName, apiKey, null, null, ApiEndPoints.ACCESS_TOKEN, null);
			System.out.println("Access token API response: " + response);

			JsonPath jsonPath = new JsonPath(response);

			if (jsonPath.get("result.token") == null) {
				System.err.println("Token key not found in response: " + response);
				return null;
			}

			String token = jsonPath.getString("result.token");
			System.out.println("Access token obtained successfully: " + token);
			return token;
		} catch (Exception e) {
			System.err.println("Failed to get access token: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}



	/**
	 * Retrieves the list of devices and extracts the first device ID.
	 * @param token The authentication token.
	 * @return The device ID string, or null if an error occurs.
	 */
	private String getDeviceList(String token) {
		try {
			String deviceListBody = JsonPayload.getDeviceListRequest(token);
			JsonPath deviceListJson = JsonUtil.printFormattedJson(
					ApiMethods.executeApiCall("POST", null, null, null, deviceListBody, ApiEndPoints.DEVICE_LIST, null));
			String deviceId = deviceListJson.getString("result.models[0].id");
			System.out.println("Device ID obtained: " + deviceId);
			return deviceId;
		} catch (Exception e) {
			System.err.println("Failed to get device list or device ID: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves and prints details for a specific device.
	 * @param token The authentication token.
	 * @param deviceId The ID of the device to get details for.
	 */
	private void getDeviceDetails(String token, String deviceId) {
		try {
			System.out.println("Getting device details for device ID: " + deviceId);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null,
					JsonPayload.getDeviceDetailsRequest(token, deviceId), ApiEndPoints.GET_DEVICES_DETAILS, null));
			System.out.println("Device details obtained successfully.");
		} catch (Exception e) {
			System.err.println("Failed to get device details: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Books a specific device and returns the booking ID (rid).
	 * @param token The authentication token.
	 * @param deviceId The ID of the device to book.
	 * @return The booking ID (rid), or -1 if an error occurs.
	 */
	private int bookDevice(String token, String deviceId) {
		try {
			String bookDeviceBody = JsonPayload.bookDeviceRequest(token, deviceId);
			JsonPath bookDeviceJson = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null,
					bookDeviceBody, ApiEndPoints.BOOK_DEVICE, null));
			int rid = bookDeviceJson.getInt("result.rid");
			System.out.println("Device booked with RID: " + rid);
			return rid;
		} catch (Exception e) {
			System.err.println("Failed to book device: " + e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Lists APKs in the app drive and finds the target file.
	 * @param token The authentication token.
	 */
	private void listAPKs(String token) {
		try {
			String apkDrive = JsonPayload.appDrive(token);
			JsonPath listOfApps = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, apkDrive, ApiEndPoints.DRIVE, null));
			Map<String, Object> matchedFile = SearchInstallAPP.findFileByName(listOfApps, targetFileName);
			SearchInstallAPP.printFileDetails(matchedFile);
			System.out.println("APK list obtained and target file details printed.");
		} catch (Exception e) {
			System.err.println("Failed to list APKs: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Installs the target application on the booked device.
	 * @param token The authentication token.
	 * @param rid The booking ID of the device.
	 */
	private void installApp(String token, int rid) {
		try {
			String installAppBody = JsonPayload.installApp(token, rid, targetFileName);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null,
					installAppBody, ApiEndPoints.INSTALL_APP, null));
			System.out.println("App installed successfully.");
		} catch (Exception e) {
			System.err.println("Failed to install app: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Closes the app using V1 API.
	 * @param token The authentication token.
	 * @param rid The booking ID of the device.
	 */
	private void closeAppV1(String token, int rid) {
		try {
			System.out.println("Closing app (V1)...");
			String payload = JsonPayload.closeApp(token, rid);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, payload, ApiEndPoints.CLOSE_APP, null));
			System.out.println("Closing app (V1) completed successfully.");
		} catch (Exception e) {
			System.err.println("Failed to close app (V1): " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Opens the app using V1 API.
	 * @param token The authentication token.
	 * @param rid The booking ID of the device.
	 */
	private void openAppV1(String token, int rid) {
		try {
			System.out.println("Opening app (V1)...");
			String payload = JsonPayload.openApp(token, rid, targetFileName);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, payload, ApiEndPoints.OPEN_APP, null));
			System.out.println("Opening app (V1) completed successfully.");
		} catch (Exception e) {
			System.err.println("Failed to open app (V1): " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Closes the app using V2 API.
	 * @param token The authentication token.
	 * @param rid The booking ID of the device.
	 */
	private void closeAppV2(String token, int rid) {
		try {
			System.out.println("Closing app (V2)...");
			String payload = JsonPayload.closeAppv2(rid);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, payload, ApiEndPoints.CLOSE_APPV2, null));
			System.out.println("Closing app (V2) completed successfully.");
		} catch (Exception e) {
			System.err.println("Failed to close app (V2): " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Opens the app using V2 API.
	 * @param token The authentication token.
	 * @param rid The booking ID of the device.
	 */
	private void openAppV2(String token, int rid) {
		try {
			System.out.println("Opening app (V2)...");
			String payload = JsonPayload.openAppv2(rid, targetFileName);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, payload, ApiEndPoints.OPEN_APPV2, null));
			System.out.println("Opening app (V2) completed successfully.");
		} catch (Exception e) {
			System.err.println("Failed to open app (V2): " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Sends text to the device using V1 API.
	 * @param token The authentication token.
	 * @param rid The booking ID of the device.
	 */
	private void sendTextV1(String token, int rid) {
		try {
			System.out.println("Sending text (V1)...");
			String payload = JsonPayload.sendText(token, rid);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, payload, ApiEndPoints.SEND_TEXT, null));
			System.out.println("Sending text (V1) completed successfully.");
		} catch (Exception e) {
			System.err.println("Failed to send text (V1): " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Sends text to the device using V2 API.
	 * @param token The authentication token.
	 * @param rid The booking ID of the device.
	 */
	private void sendTextV2(String token, int rid) {
		try {
			System.out.println("Sending text (V2)...");
			String payload = JsonPayload.sendTextV2(rid);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, payload, ApiEndPoints.SEND_TEXTV2, null));
			System.out.println("Sending text (V2) completed successfully.");
		} catch (Exception e) {
			System.err.println("Failed to send text (V2): " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Rotates the device using V1 API.
	 * @param token The authentication token.
	 * @param rid The booking ID of the device.
	 */
	private void rotateDeviceV1(String token, int rid) {
		try {
			System.out.println("Rotating device (V1)...");
			String payload = JsonPayload.roateDevice(token, rid);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, payload, ApiEndPoints.ROTATE_DEVICE, null));
			System.out.println("Rotating device (V1) completed successfully.");
		} catch (Exception e) {
			System.err.println("Failed to rotate device (V1): " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Rotates the device using V2 API.
	 * @param token The authentication token.
	 * @param rid The booking ID of the device.
	 */
	private void rotateDeviceV2(String token, int rid) {
		try {
			System.out.println("Rotating device (V2)...");
			String payload = JsonPayload.roateDeviceV2(rid);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, payload, ApiEndPoints.ROTATE_DEVICEV2, null));
			System.out.println("Rotating device (V2) completed successfully.");
		} catch (Exception e) {
			System.err.println("Failed to rotate device (V2): " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Performs a tap action on the device screen.
	 * @param token The authentication token.
	 * @param rid The booking ID of the device.
	 */
	private void tapOnDevice(String token, int rid) {
		try {
			System.out.println("Tapping on screen...");
			String payload = JsonPayload.tap(token, rid);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, payload, ApiEndPoints.TAP, null));
			System.out.println("Tapping on screen completed successfully.");
		} catch (Exception e) {
			System.err.println("Failed to tap on device: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Releases the booked device.
	 * @param token The authentication token.
	 * @param rid The booking ID of the device.
	 */
	private void releaseDevice(String token, int rid) {
		try {
			System.out.println("Releasing device...");
			String payload = JsonPayload.releaseDevice(token, rid);
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, payload, ApiEndPoints.RELEASE_DEVICE, null));
			System.out.println("Device released successfully.");
		} catch (Exception e) {
			System.err.println("Failed to release device: " + e.getMessage());
			e.printStackTrace();
		}
	}
}

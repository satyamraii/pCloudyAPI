package com.api.testing.main;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.File;

import com.api.testing.utils.ApiEndPoints;
import com.api.testing.utils.ApiMethods;
import com.api.testing.utils.JsonPayload;
import com.api.testing.utils.JsonUtil;

import io.restassured.path.json.JsonPath;

public class VisualOCR {

	private String userName = "satyam.kumar@sstsinc.com";
	private String apiKey = "zq76hq3yt7v2stmzshzqp8xf";

	private String token;
	private String deviceId;
	private int rid;
	private String fileId1;
	private String fileId2;

	@BeforeClass
	public void setup() {
		try {
			token = getAccessToken();
			deviceId = getDeviceId(token);
			getDeviceDetails(token, deviceId);
			rid = bookDevice(token, deviceId);
			fileId1 = uploadImage(token, "/home/administrator/Image Collection/demoimg.png");
			fileId2 = uploadImage(token, "/home/administrator/Image Collection/Demoimg2.png");
		} catch (Exception e) {
			System.err.println("Setup failed: " + e.getMessage());
			Assert.fail("Test setup failed due to: " + e.getMessage());
		}
	}

	@AfterClass
	public void tearDown() {
		try {
			releaseDevice(token, rid);
		} catch (Exception e) {
			System.err.println("Device release failed: " + e.getMessage());
		}
	}

	@Test(priority = 1)
	public void testTextExist() {
		try {
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token,
					JsonPayload.textExist(rid, fileId1), ApiEndPoints.TEXT_EXIST, null));
		} catch (Exception e) {
			Assert.fail("TextExist API failed: " + e.getMessage());
		}
	}

	@Test(priority = 2)
	public void testTextCoordinates() {
		try {
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token,
					JsonPayload.textCoordinate(rid, fileId1), ApiEndPoints.TEXT_COORDINATE, null));
		} catch (Exception e) {
			Assert.fail("TextCoordinate API failed: " + e.getMessage());
		}
	}

	@Test(priority = 3)
	public void testGetAllOcrText() {
		try {
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token,
					JsonPayload.getAllText(rid, fileId1), ApiEndPoints.OCR_ALLTEXT, null));
		} catch (Exception e) {
			Assert.fail("OCR All Text API failed: " + e.getMessage());
		}
	}

	@Test(priority = 4)
	public void testCompareImages() {
		try {
			JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token,
					JsonPayload.imageCompare(rid, fileId1, fileId2), ApiEndPoints.OCR_IMAGECOMPARE, null));
		} catch (Exception e) {
			Assert.fail("Image Compare API failed: " + e.getMessage());
		}
	}

	// ======================== Helper Methods ========================

	private String getAccessToken() throws Exception {
		String response = ApiMethods.executeApiCall("GET", userName, apiKey, null, null, ApiEndPoints.ACCESS_TOKEN, null);
		if (response == null || !response.contains("\"token\"")) {
			throw new Exception("Access token not found in response.");
		}
		return response.split("\"token\":\"")[1].split("\"")[0];
	}

	private String getDeviceId(String token) throws Exception {
		String deviceListBody = JsonPayload.getDeviceListRequest(token);
		JsonPath json = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, deviceListBody, ApiEndPoints.DEVICE_LIST, null));
		String deviceId = json.getString("result.models[0].id");
		if (deviceId == null || deviceId.isEmpty()) {
			throw new Exception("Device ID not found.");
		}
		return deviceId;
	}

	private void getDeviceDetails(String token, String deviceId) throws Exception {
		JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null,
				JsonPayload.getDeviceDetailsRequest(token, deviceId), ApiEndPoints.GET_DEVICES_DETAILS, null));
	}

	private int bookDevice(String token, String deviceId) throws Exception {
		JsonPath json = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null,
				JsonPayload.bookDeviceRequest(token, deviceId), ApiEndPoints.BOOK_DEVICE, null));
		return json.getInt("result.rid");
	}

	private String uploadImage(String token, String filePath) throws Exception {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("File not found: " + filePath);
		}
		JsonPath json = JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, token, null,
						ApiEndPoints.UPLOAD_IMAGE, file));
		String fileId = json.getString("data.fileId");
		if (fileId == null || fileId.isEmpty()) {
			throw new Exception("File upload failed for: " + filePath);
		}
		return fileId;
	}

	private void releaseDevice(String token, int rid) throws Exception {
		String releaseBody = JsonPayload.releaseDevice(token, rid);
		JsonPath response = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, releaseBody, ApiEndPoints.RELEASE_DEVICE, null));
		if (!"true".equalsIgnoreCase(response.getString("result"))) {
			throw new Exception("Device release failed. Response: " + response.prettify());
		}
		System.out.println("Device has been released");
	}
}
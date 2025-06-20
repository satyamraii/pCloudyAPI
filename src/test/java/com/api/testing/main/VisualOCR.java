package com.api.testing.main;

import org.testng.annotations.Test;
import java.io.File;

import org.testng.annotations.Test;

import com.api.testing.utils.ApiEndPoints;
import com.api.testing.utils.ApiMethods;
import com.api.testing.utils.JsonPayload;
import com.api.testing.utils.JsonUtil;

import io.restassured.path.json.JsonPath;

public class VisualOCR {

	@Test
	public void visualOCR() {

		String userName = "satyam.kumar@sstsinc.com";
		String apiKey = "hmww27d3dj44rs4zmxbx8vhf";

		// Get access token
		String response = ApiMethods.executeApiCall("GET", userName, apiKey, null, null, ApiEndPoints.ACCESS_TOKEN, null);
		String token = response.split("\"token\":\"")[1].split("\"")[0];

		// Get device list and first deviceIdsss
		String deviceListBody = JsonPayload.getDeviceListRequest(token);
		JsonPath deviceListJson = JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, null, deviceListBody, ApiEndPoints.DEVICE_LIST, null));

		String deviceId = deviceListJson.getString("result.models[0].id");

		// Get device details
		JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, null,
						JsonPayload.getDeviceDetailsRequest(token, deviceId), ApiEndPoints.GET_DEVICES_DETAILS, null));

		// Book the device
		JsonPath bookDeviceJson = JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, null,
						JsonPayload.bookDeviceRequest(token, deviceId), ApiEndPoints.BOOK_DEVICE, null));

		int rid = bookDeviceJson.getInt("result.rid");

		// Upload file 1
		String fileId1 = JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, token, null,
						ApiEndPoints.UPLOAD_IMAGE, new File("/home/administrator/Image Collection/demoimg.png")))
				.getString("data.fileId");

		// Upload file 2
		String fileId2 = JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, token, null,
						ApiEndPoints.UPLOAD_IMAGE, new File("/home/administrator/Image Collection/Demoimg2.png")))
				.getString("data.fileId");

		// Verify text exists
		JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, token,
						JsonPayload.textExist(rid, fileId1), ApiEndPoints.TEXT_EXIST, null));

		// Verify text coordinates
		JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, token,
						JsonPayload.textCoordinate(rid, fileId1), ApiEndPoints.TEXT_COORDINATE, null));

		// Get all OCR text
		JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, token,
						JsonPayload.getAllText(rid, fileId1), ApiEndPoints.OCR_ALLTEXT, null));

		// Compare images
		JsonUtil.printFormattedJson(
				ApiMethods.executeApiCall("POST", null, null, token,
						JsonPayload.imageCompare(rid, fileId1, fileId2), ApiEndPoints.OCR_IMAGECOMPARE, null));

		//Release device
		String releaseDevice= JsonPayload.releaseDevice(token, rid);
		System.out.println("Request response is: "+ releaseDevice);
		JsonPath deviceRelease= JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, releaseDevice, ApiEndPoints.RELEASE_DEVICE, null));
		System.out.println("Device has been released");
	}

}

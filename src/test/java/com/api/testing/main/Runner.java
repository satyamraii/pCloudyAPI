package com.api.testing.main;

import org.testng.annotations.Test;
import org.testng.annotations.Test;

import com.api.testing.utils.ApiEndPoints;
import com.api.testing.utils.ApiMethods;
import com.api.testing.utils.JsonPayload;

import io.restassured.path.json.JsonPath;

public class Runner {

	@Test
	public void androidAPITest() {

		String userName="satyam.kumar@sstsinc.com";
		String apiKey="trdwz5qr3243zvd5hxfdtp78";


		// To Get the access token
		String response= ApiMethods.executeApiCall("GET" , userName, apiKey, null, null, ApiEndPoints.ACCESS_TOKEN, null);
		String token = response.split("\"token\":\"")[1].split("\"")[0];

		// TO Get device list
		String deviceListBody = JsonPayload.getDeviceListRequest(token);
		System.out.println("Request JSON:\n" + deviceListBody);

		String rawResponse = ApiMethods.executeApiCall("POST", null, null, null, deviceListBody, ApiEndPoints.DEVICE_LIST, null);

		JsonPath json = new JsonPath(rawResponse);
		System.out.println("Response JSON:\n" + json.prettify());

		String deviceId = json.getString("result.models[0].id");
		System.out.println("Device ID (as String): " + deviceId);


		//To Get deviceDetails
		String deviceDetailsBody = JsonPayload.getDeviceDetailsRequest(token, deviceId);
		System.out.println("Request JSON:\n" + deviceDetailsBody);

		String getDeviceDetails = ApiMethods.executeApiCall("POST", null, null, null, deviceDetailsBody, ApiEndPoints.GET_DEVICES_DETAILS, null);

		JsonPath json1 = new JsonPath(getDeviceDetails);
		System.out.println("Response JSON:\n" + json1.prettify());

		//To Book the device
		String bookDeviceBody= JsonPayload.bookDeviceRequest(token, deviceId);
		System.out.println("Request JSON:\n" + bookDeviceBody);

		String bookDevice = ApiMethods.executeApiCall("POST", null, null, null, bookDeviceBody, ApiEndPoints.BOOK_DEVICE, null);

		JsonPath json2 = new JsonPath(bookDevice);
		System.out.println("Response JSON:\n" + json2.prettify());

		int rid = json2.getInt("result.rid");
		System.out.println("RID (as int): " + rid);

		//To Set Passcode
		String setPasscodeAndroid = JsonPayload.setPassCode(deviceId, rid);
		System.out.println("Request JSON:\n" + setPasscodeAndroid);

		String setPasscode = ApiMethods.executeApiCall("POST", null, null, token, setPasscodeAndroid, ApiEndPoints.SET_PASSCODE, null);
		
        JsonPath json3 = new JsonPath(setPasscode);
		System.out.println("Response JSON:\n" + json3.prettify());

		//To Reset Passcode
        String resetPasscodeAndroid = JsonPayload.reSetPassCode(deviceId, rid); //Calling payload here
		System.out.println("Request JSON:\n" + resetPasscodeAndroid);

		String resetPassCode = ApiMethods.executeApiCall("POST", null, null, token, resetPasscodeAndroid, ApiEndPoints.RESET_PASSCODE, null); //calling body part here

		JsonPath json4 = new JsonPath(resetPassCode);
		System.out.println("Response JSON:\n" + json4.prettify());

		//To check the live view

		String initLiveview = JsonPayload.initLiveView(rid);
		System.out.println("Request JSON:\n" + initLiveview);

		String connectLiveView = ApiMethods.executeApiCall("POST", null, null, token, initLiveview, ApiEndPoints.INIT_LIVEVIEW, null);

		JsonPath json5 = new JsonPath(connectLiveView);
		System.out.println("Response JSON:\n" + json5.prettify());
		
		
		
		
	}



}

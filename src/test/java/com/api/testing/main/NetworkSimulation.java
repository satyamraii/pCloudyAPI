package com.api.testing.main;

import org.testng.annotations.Test;
import java.util.Map;

import org.testng.annotations.Test;

import com.api.testing.utils.ApiEndPoints;
import com.api.testing.utils.ApiMethods;
import com.api.testing.utils.JsonPayload;
import com.api.testing.utils.JsonUtil;
import com.api.testing.utils.SearchInstallAPP;

import io.restassured.path.json.JsonPath;


public class NetworkSimulation {
	
    @Test
	public void NetworkSimulationAPI(){

	String userName = "satyam.kumar@sstsinc.com";
	String apiKey = "hmww27d3dj44rs4zmxbx8vhf";

	// Get access token
	String response = ApiMethods.executeApiCall("GET", userName, apiKey, null, null, ApiEndPoints.ACCESS_TOKEN, null);
	String token = response.split("\"token\":\"")[1].split("\"")[0];

	// Get device list and first deviceId
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
	System.out.println("Device hasbeen booked");
	
	//Get List of Profile
	
	String profileList= JsonPayload.getSimulationProfiles(rid);
	System.out.println("Request response is: "+ profileList);
	
	JsonPath getListProfile =  JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, profileList, ApiEndPoints.LIST_PROFILES, null));
	System.out.println("Here is the list of profile");
	
	//Start NetworkSimulation
	
	String startSimulation = JsonPayload.startNetworkSimulation(rid);
	System.out.println("Request response is: "+ startSimulation );
	
	JsonPath startNetworkSimulation = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, startSimulation, ApiEndPoints.SIMULATION_START, null));
	System.out.println("Network simulation has been started");
	
	//Device-Profile
	String GetdeviceProfile = JsonPayload.deviceProfile(rid);
	System.out.println("Request response is: "+ GetdeviceProfile);
	
	JsonPath currentDeviceProfile= JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, GetdeviceProfile, ApiEndPoints.DEVICE_PROFILE, null));
	System.out.println("Here is the current device profile");
	
	//Stop Network-Simulation
	String stopSimulation = JsonPayload.stopNetworkSimulation(rid);
	System.out.println("Request response is: "+ stopSimulation);
	
	JsonPath stopNetworkSimulation = JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, stopSimulation, ApiEndPoints.SIMULATION_STOP, null));
	System.out.println("Network Simulation has been stopped");
	
	//Release device
	String releaseDevice= JsonPayload.releaseDevice(token, rid);
	System.out.println("Request response is: "+ releaseDevice);
	JsonPath deviceRelease= JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, releaseDevice, ApiEndPoints.RELEASE_DEVICE, null));
	System.out.println("Device has been released");
	
}
}
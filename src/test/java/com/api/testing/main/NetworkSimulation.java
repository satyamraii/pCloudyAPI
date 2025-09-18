package com.api.testing.main;

import org.testng.annotations.Test;
import java.util.Map;

import com.api.testing.utils.ApiMethods;
import com.api.testing.utils.ApiEndPoints;
import com.api.testing.utils.JsonPayload;
import com.api.testing.utils.JsonUtil;
import com.api.testing.utils.SearchInstallAPP;

import io.restassured.path.json.JsonPath;

public class NetworkSimulation {

    @Test
    public void NetworkSimulationAPI() {
        String userName = "satyam.kumar@sstsinc.com";
        String apiKey = "zq76hq3yt7v2stmzshzqp8xf";
        String token = null;
        String deviceId = null;
        int rid = -1;

        try {
            token = getAccessToken(userName, apiKey);
            deviceId = getDeviceId(token);
            getDeviceDetails(token, deviceId);
            rid = bookDevice(token, deviceId);
            getSimulationProfiles(token, rid);
            startNetworkSimulation(token, rid);
            getDeviceProfile(token, rid);
            stopNetworkSimulation(token, rid);
        } catch (Exception e) {
            System.err.println("Error during Network Simulation: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (token != null && rid != -1) {
                releaseDevice(token, rid);
            }
        }
    }

    private String getAccessToken(String userName, String apiKey) {
        try {
            String response = ApiMethods.executeApiCall("GET", userName, apiKey, null, null, ApiEndPoints.ACCESS_TOKEN, null);
            String token = response.split("\"token\":\"")[1].split("\"")[0];
            System.out.println("Access token obtained: " + token);
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get access token: " + e.getMessage(), e);
        }
    }

    private String getDeviceId(String token) {
        try {
            String deviceListBody = JsonPayload.getDeviceListRequest(token);
            JsonPath deviceListJson = JsonUtil.printFormattedJson(
                ApiMethods.executeApiCall("POST", null, null, null, deviceListBody, ApiEndPoints.DEVICE_LIST, null));
            String deviceId = deviceListJson.getString("result.models[0].id");
            System.out.println("Device ID obtained: " + deviceId);
            return deviceId;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get device ID: " + e.getMessage(), e);
        }
    }

    private void getDeviceDetails(String token, String deviceId) {
        try {
            JsonUtil.printFormattedJson(
                ApiMethods.executeApiCall("POST", null, null, null,
                    JsonPayload.getDeviceDetailsRequest(token, deviceId), ApiEndPoints.GET_DEVICES_DETAILS, null));
            System.out.println("Device details fetched.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get device details: " + e.getMessage(), e);
        }
    }

    private int bookDevice(String token, String deviceId) {
        try {
            JsonPath bookDeviceJson = JsonUtil.printFormattedJson(
                ApiMethods.executeApiCall("POST", null, null, null,
                    JsonPayload.bookDeviceRequest(token, deviceId), ApiEndPoints.BOOK_DEVICE, null));
            int rid = bookDeviceJson.getInt("result.rid");
            System.out.println("Device booked with RID: " + rid);
            return rid;
        } catch (Exception e) {
            throw new RuntimeException("Failed to book device: " + e.getMessage(), e);
        }
    }

    private void getSimulationProfiles(String token, int rid) {
        try {
            String profileList = JsonPayload.getSimulationProfiles(rid);
            System.out.println("Request response is: " + profileList);
            JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, profileList, ApiEndPoints.LIST_PROFILES, null));
            System.out.println("Simulation profiles listed.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get simulation profiles: " + e.getMessage(), e);
        }
    }

    private void startNetworkSimulation(String token, int rid) {
        try {
            String startSimulation = JsonPayload.startNetworkSimulation(rid);
            System.out.println("Request response is: " + startSimulation);
            JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, startSimulation, ApiEndPoints.SIMULATION_START, null));
            System.out.println("Network simulation started.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to start network simulation: " + e.getMessage(), e);
        }
    }

    private void getDeviceProfile(String token, int rid) {
        try {
            String getDeviceProfile = JsonPayload.deviceProfile(rid);
            System.out.println("Request response is: " + getDeviceProfile);
            JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, getDeviceProfile, ApiEndPoints.DEVICE_PROFILE, null));
            System.out.println("Device profile fetched.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get device profile: " + e.getMessage(), e);
        }
    }

    private void stopNetworkSimulation(String token, int rid) {
        try {
            String stopSimulation = JsonPayload.stopNetworkSimulation(rid);
            System.out.println("Request response is: " + stopSimulation);
            JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, token, stopSimulation, ApiEndPoints.SIMULATION_STOP, null));
            System.out.println("Network simulation stopped.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to stop network simulation: " + e.getMessage(), e);
        }
    }

    private void releaseDevice(String token, int rid) {
        try {
            String releaseDevice = JsonPayload.releaseDevice(token, rid);
            System.out.println("Request response is: " + releaseDevice);
            JsonUtil.printFormattedJson(ApiMethods.executeApiCall("POST", null, null, null, releaseDevice, ApiEndPoints.RELEASE_DEVICE, null));
            System.out.println("Device released.");
        } catch (Exception e) {
            System.err.println("Failed to release device: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
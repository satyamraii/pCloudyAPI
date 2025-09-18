package com.api.testing.utils;

public class JsonPayload {
	
	//To get device list
	public static String getDeviceListRequest(String token) {
        return "{\n" +
                "  \"token\": \"" + token + "\",\n" +
                "  \"duration\": 70,\n" +
                "  \"platform\": \"ios\",\n" +
                "  \"available_now\": \"true\"\n" +
                "}";
    }

	//To get device details
    public static String getDeviceDetailsRequest(String token, String deviceId) {
        return "{\n" +
                "  \"token\": \"" + token + "\",\n" +
                "  \"id\": \"" + deviceId + "\",\n" +
                "  \"full_name\": \"any\",\n" +
                "  \"platform\": \"any\",\n" +
                "  \"manufacturer\": \"any\",\n" +
                "  \"version\": \"any\",\n" +
                "  \"model\": \"any\",\n" +
                "  \"number\": \"any\",\n" +
                "  \"duration\": 15,\n" +
                "  \"available_now\": \"false\"\n" +
                "}";
    }
    
    //To book a device
    public static String bookDeviceRequest(String token,String deviceId) {
    	
    	return "{\n"
    		    + "    \"token\": \"" + token + "\",\n"
    		    + "    \"duration\": 30,\n"
    		    + "    \"id\": \"" + deviceId + "\"\n"
    		    + "}";
    }
    
    //To Set passcode
    public static String setPassCode(String deviceId, int rid) {
        return "{\n"
             + "    \"rid\": " + rid + ",\n"
             + "    \"passcode\": \"set\"\n"
             + "}";
    }
    
    //To Reset Passcode
    public static String reSetPassCode(String deviceId, int rid) {
        return "{\n"
        		+ "    \"rid\":" + rid + ",\n"
        		+ "    \"passcode\": \"reset\"\n"
        		+ "}";
    }
    
    //To check LiveView
    public static String initLiveView (int rid) {

    	return "{\n"
    		    + "    \"rid\": " + rid + "\n"
    		    + "}";

    }
    // To verify text-exist
    public static String textExist (int rid,String fileId1) {

    	return "{\n"
    		    + "    \"imageId\": \"" + fileId1 + "\",\n"
    		    + "    \"word\": \"REFUSE\",\n"
    		    + "    \"rid\": " + rid + "\n"
    		    + "}";
}
    //To check co-ordinate
    public static String textCoordinate (int rid,String fileId1) {

    	return "{\n"
    		    + "    \"imageId\": \"" + fileId1 + "\",\n"
    		    + "    \"word\": \"REFUSE\",\n"
    		    + "    \"rid\": " + rid + "\n"
    		    + "}";
}
    //To get All-Text
    public static String getAllText (int rid,String fileId1) {

    	return "{\n"
    		    + "    \"imageId\": \"" + fileId1 + "\",\n"
    		    + "    \"word\": \"REFUSE\",\n"
    		    + "    \"rid\": " + rid + "\n"
    		    + "}";
}
    
    //To compare-images
    public static String imageCompare (int rid,String fileId1,String fileId2) {

    	return "{\n"
    		    + "    \"firstImageId\": \"" + fileId1 + "\",\n"
    		    + "    \"secondImageId\": \"" + fileId2 + "\",\n"
    		    + "    \"rid\": " + rid + "\n"
    		    + "}";
}
    
    //APk Drive
    public static String appDrive (String token) {

    	return "{\n"
    		    + "    \"token\": \"" + token + "\",\n"
    		    + "    \"limit\": \"all\",\n"
    		    + "    \"filter\": \"all\"\n"
    		    + "}";
    }
    
    //Install App
    public static String installApp (String token,int rid,String targetFileName) {

    	return  "{\n"
    		    + "    \"token\": \"" + token + "\",\n"
    		    + "    \"rid\": " + rid + ",\n"
    		    + "    \"filename\": \"" + targetFileName + "\",\n"
    		    + "    \"grant_all_permissions\": \"true\"\n"
    		    + "}";
    
    
}
    //Close-App
    public static String closeApp (String token,int rid) {

    	return  "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"rid\": " + rid + ",\n"
    			+ "    \"bundleId\": \"com.pcloudy.TestmunkDemo\"\n"
    			+ "}";
    }

    //Open-App
    public static String openApp (String token,int rid,String targetFileName) {

    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"rid\": " + rid + ",\n"
    			+ "    \"filename\": \"" + targetFileName + "\",\n"
    			+ "    \"bundleId\": \"com.pcloudy.TestmunkDemo\"\n"
    			+ "}";
    }
    
    //Close-App (V2)
    
    public static String closeAppv2 (int rid) {

    	return "{\n"
    			+ "    \"rid\": " + rid + ",\n"
    			+ "    \"bundleId\": \"com.pcloudy.TestmunkDemo\"\n"
    			+ "}";
    }
    
    //Open-App (V2)
    
    public static String openAppv2 (int rid,String targetFileName) {

    	return "{\n"
    			+ "    \"rid\": " + rid + ",\n"
    			+ "    \"fileName\": \"" + targetFileName + "\",\n"
    			+ "    \"bundleId\": \"com.pcloudy.TestmunkDemo\"\n"
    			+ "}";
    }
    
    //Send Text
    public static String sendText (String token, int rid) {

    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"rid\": " + rid + ",\n"
    			+ "    \"text\":\"Bangalore India Karnataka\"\n"
    			+ "}";
    }
    
    //Send-Text(V2)
    public static String sendTextV2 (int rid) {

    	return "{\n"
    			+ "    \"rid\": " + rid + ",\n"
    			+ "    \"text\": \"Bangalore India Karnataka\"\n"
    			+ "}";
    }
    
    //Rotate-device
    public static String roateDevice (String token, int rid) {

    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"rotate\": \"P\",\n"
    			+ "    \"rid\": " + rid + "\n"
    			+ "}";
    }
    //Rotate-device(V2)
    public static String roateDeviceV2 (int rid) {

    	return "{\n"
    			+ "    \"rotate\": \"P\",\n"
    			+ "    \"rid\": " + rid + "\n"
    			+ "}";
    }
    
    //Tap on Screen
    public static String tap (String token,int rid) {

    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"location\": \"153,313,0,0\",\n"
    			+ "    \"rid\": " + rid + "\n"
    			+ "}";
    }
    
    //Get List of Profiles
    public static String getSimulationProfiles (int rid) {

    	return "{\n"
    			+ "    \"rid\": " + rid + "\n"
    			+ "}";
    }
    
   //Start Network-Simulation
    public static String startNetworkSimulation (int rid) {
    	
    	//IOS:4G-Advance-Average
    	//Android: 4G-Advanced-Average

    	return "{\n"
    			+  "    \"rid\": " + rid + ",\n"
    			+ "    \"profileName\": \"4G-Advance-Average\"\n"
    			+ "}";
    }
    
   
    //Device Profile
    public static String deviceProfile (int rid) {

    	return "{\n"
    			+ "    \"rid\": " + rid + "\n"
    			+ "}";
    	
    }
    
    //Stop Network-Simulation
    public static String stopNetworkSimulation (int rid) {

    	return "{\n"
    			+ "    \"rid\": " + rid + "\n"
    			+ "}";
    
    }
    
    //Release Device
    public static String releaseDevice (String token,int rid) {

    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"rid\": " + rid + "\n"
    			+ "}";
    
    }
    
    //Get UserInfo
    public static String getUserInfo () {

    	return "";

    }
    
    //appium init(v2)
    public static String appiumInitV2 (int deviceId) {

    	return "{\n"
    		    + "    \"duration\": 30,\n"
    		    + "    \"did\": " + deviceId + ",\n"
    		    + "    \"sessionName\": \"testingappiumprod\"\n"
    		    + "}";
    }
    
    //appium init
    public static String appiumInit (String token,int deviceId) {

    	return "{\n"
    	        + "    \"token\": \"" + token + "\",\n"
    	        + "    \"duration\": 30,\n"
    	        + "    \"platform\": \"ios\",\n"
    	        + "    \"devices\": [" + deviceId + "],\n"
    	        + "    \"session\": \"Deviceondemand_1\",\n"
    	        + "    \"overwrite_location\": \"true\"\n"
    	        + "}";
    }
    
    //Unlock-device IOS
    public static String unlockDeviceIOS (int rid) {

    	return "{\n"
    		    + "    \"rid\": " + rid + "\n"
    		    + "}";
    }
    
    
    //Lonely-Appium
    public static String lonelyAppium (String token) {

    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"package\": \"com.pcloudy.TestmunkDemo\"\n"
    			+ "}";
    }
    
    //Capture device-screenshotne
    public static String captureDeviceScreenshot (String token, int rid) {

    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+  "    \"rid\": " + rid + ",\n"
    			+ "    \"skin\":\"true\"\n"
    			+ "  }";
    }
    
    //Get-Player(v2)
    public static String getPlayerV2 (int rid) {

    	return "{\n"
    		    + "    \"rid\": " + rid + "\n"
    		    + "}";
    }
    
    //start device-service
    public static String startDeviceService (String token, int rid) {

    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+  "    \"rid\": " + rid + ",\n"
    			+ "    \"startDeviceLogs\": true,\n"
    			+ "    \"startPerformanceData\": true,\n"
    			+ "    \"startSessionRecording\": true\n"
    			+ "}";
    	
    }
    
    //GetAppium Filelist
    public static String getAppiumFileList (String token, int rid) {

    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"rid\": " + rid + "\n"
    			+ "}";
    }
    
    //download appium-access-data
    public static String downloadAppiumData (String token, int rid,String getFileName) {

    	return "{\n"
    		    + "    \"token\": \"" + token + "\",\n"
    		    + "    \"rid\": " + rid + ",\n"
    		    + "    \"filename\": \"" + getFileName + "\"\n"
    		    + "}";
    }
    
    //Endpoint URL
    public static String endpointURL (String token) {

    	return "{\n"
    		    + "    \"token\": \"" + token + "\"\n"
    		    + "}";
    
    }
    
    //Get AppiumReportFolder
    public static String getAppiumReportFolder (String token) {

    	return "{\n"
    		    + "    \"token\": \"" + token + "\"\n"
    		    + "}";
    
    }
    
    //GetAppiumShareable ReportLink
    public static String shareableReportLink (String token,int rid) {

    	return"{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"rid\": " + rid + "\n"
    			+ "}";
    
    }
    
    //download ManualAccessData
    public static String downloadManualAccessData (String token,int rid) {

    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+  "    \"rid\": " + rid + ",\n"
    			+ "    \"filename\": \"Screen_-17442806851-F.jpg\"\n"
    			+ "}";
    }
    
    //DownloadFile
    public static String downloadFile (String token) {

    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"filename\": \"QR_code_G.png\",\n"
    			+ "    \"dir\": \"data\"\n"
    			+ "}";
    }
    
    //Get Available AppiumVersion
    public static String getAvailableAppiumVersion (String token) {

    	return "{\n"
    		    + "    \"token\": \"" + token + "\"\n"
    		    + "}";
    }
    
    //XCUITest Automation Booking
    public static String xcuiTestDeviceBooking(String token,int deviceId) {
    	
		return "{\n" +
			    "    \"token\": \"" + token + "\",\n" +
			    "    \"devices\": [" + deviceId + "],\n" +
			    "    \"booking_duration\": \"15\",\n" +
			    "    \"automation_type\": \"XCUITest\"\n" +
			    "}";
    	
    }
    
    //XCtest init
    public static String xcTestINIT(int automationId,String targetFileName) {
    	
    	return "{\n" +
    		    "    \"automationId\": " + automationId + ",\n" +
    		    "    \"test_suite\": \"" + targetFileName + "\"\n" +
    		    "}";
    }
    
    //XCTest startdeviceservice
    public static String xcTestStartDeviceService(String token,int automationId) {
    	
    	return "{\n" +
    		    "    \"token\": \"" + token + "\",\n" +
    		    "    \"automationId\": " + automationId + ",\n" +
    		    "    \"startDeviceLogs\": \"true\",\n" +
    		    "    \"startPerformanceData\": \"true\",\n" +
    		    "    \"startSessionRecording\": \"true\"\n" +
    		    "}";
    	
    }
    
    //Release XCTestDevice
    public static String releaseXCTestDevice(String token,int automationId) {
    	
    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"automationId\": " + automationId + "\n"
    			+ "}";
    }
    
    //Xctest shareable reportlink
    public static String xcTestShareableReportLink(String token,int automationId) {
    	
    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"automationId\": " + automationId + "\n"
    			+ "}";
    }
    
    //Instrumentation Initiate Biometric
    public static String initiateInstrumentationBiometric(String token, String targetFileName) {
    	
    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"fileName\": \"" + targetFileName + "\",\n"
    			+ "    \"isImageInjection\": \"false\",\n"
    			+ "    \"isBiometricAuth\": \"true\"\n"
    			+ "}";
    }
    
  //Instrumentation Initiate InjectImage
    public static String initiateInstrumentationImageInjection(String token, String targetFileName) {
        return "{\n"
            + "    \"token\": \"" + token + "\",\n"
            + "    \"fileName\": \"" + targetFileName + "\",\n"
            + "    \"isImageInjection\": true,\n"
            + "    \"isBiometricAuth\": false\n"
            + "}";
    }
    
    //Instrumentation Progress
    public static String instrumentationProgress(String token, String instrumentationToken) {
    	
    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"instrumentation-token\": \"" + instrumentationToken + "\"\n"
    			+ "}";
    }
    
    //Download Instrument APK
    public static String downloadInstrumentAPK(String token,String instrumentationToken) {
    	
    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"instrumentation-token\": \"" + instrumentationToken + "\"\n"
    			+ "}";
    }
    
    //Biometric Authentication
    public static String biometricAuth(String token, int rid) {
    	
    	return "{\n"
    			+ "  \"token\": \"" + token + "\",\n"
    			+ "  \"rid\": " + rid + ",\n"
    			+ "  \"auth_state\": true\n"
    			+ "}";
    }
    
    //Image Injection
    public static String imageInject(String token, int rid) {
        return "{\n"
            + "    \"token\": \"" + token + "\",\n"
            + "    \"rid\": " + rid + ",\n"
            + "    \"fileName\": \"QR_code_G.png\"\n"
            + "}";
    }

    //Initiating Resign
    public static String resignInitiate(String token, String targetFileName) {
    	 return "{\n" +
    	           "\t\"token\":\"" + token + "\",\n" +
    	           "\t\"filename\": \"" + targetFileName + "\"\n" +
    	           "}";
    }
    
    //ResignProgress
    public static String resignProgress(String token,String resignToken,String resignFileName) {
    	return "{\n" +
    	           "    \"token\": \"" + token + "\",\n" +
    	           "    \"resign_token\": \"" + resignToken + "\",\n" +
    	           "    \"filename\": \"" + resignFileName + "\"\n" +
    	           "}";
   }
    
    //Resign Download
    public static String resignDownload(String token,String resignToken,String resignFileName) {
    	return "{\n" +
    	           "    \"token\": \"" + token + "\",\n" +
    	           "    \"resign_token\": \"" + resignToken + "\",\n" +
    	           "    \"filename\": \"" + resignFileName + "\"\n" +
    	           "}";
   }

    //Release-device
    public static String releaseAppiumDevice (String token,int rid) {

    	return "{\n"
    			+ "    \"token\": \"" + token + "\",\n"
    			+ "    \"release_after\": 0,\n"
    			+ "    \"rid\": " + rid + "\n"
    			+ "}";
    }
    
 }

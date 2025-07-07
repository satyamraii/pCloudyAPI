package com.api.testing.utils;

public class ApiEndPoints {
	
	public static final String BASE_URL = "https://device.pcloudy.com";

    public static final String ACCESS_TOKEN = BASE_URL + "/api/access";
    public static final String DEVICE_LIST = BASE_URL + "/api/devices";
    public static final String GET_DEVICES_DETAILS = BASE_URL + "/api/get_devices_details";
    public static final String BOOK_DEVICE = BASE_URL + "/api/book_device";
    public static final String SET_PASSCODE = BASE_URL + "/api/v2/set-passcode";
    public static final String RESET_PASSCODE = BASE_URL + "/api/v2/set-passcode";
   
    public static final String UPLOAD_IMAGE = BASE_URL + "/api/v2/visual/upload-image";
    public static final String TEXT_EXIST = BASE_URL +  "/api/v2/ocr/text-exists";
    public static final String TEXT_COORDINATE = BASE_URL + "/api/v2/ocr/text-coordinate";
    public static final String OCR_ALLTEXT = BASE_URL + "/api/v2/ocr/text";
    public static final String OCR_IMAGECOMPARE = BASE_URL + "/api/v2/visual/imagecompare";
    public static final String DRIVE = BASE_URL + "/api/drive";
    public static final String INSTALL_APP = BASE_URL + "/api/install_app";
    public static final String CLOSE_APP = BASE_URL + "/api/close_app";
    public static final String OPEN_APP = BASE_URL + "/api/open_app";
    public static final String CLOSE_APPV2 = BASE_URL + "/api/v2/close-app";
    public static final String OPEN_APPV2 = BASE_URL + "/api/v2/open-app";
    public static final String SEND_TEXT = BASE_URL + "/api/send_text";
    public static final String SEND_TEXTV2 = BASE_URL + "/api/v2/send-text";
    public static final String ROTATE_DEVICE = BASE_URL + "/api/rotate_device";
    public static final String ROTATE_DEVICEV2 = BASE_URL + "/api/v2/rotate-device";
    public static final String TAP = BASE_URL + "/api/tap";
    public static final String LIST_PROFILES = BASE_URL + "/api/v2/networkshaping/list-profiles";
    public static final String SIMULATION_START = BASE_URL + "/api/v2/networkshaping/start";
    public static final String DEVICE_PROFILE = BASE_URL + "/api/v2/networkshaping/device-profile";
    public static final String SIMULATION_STOP = BASE_URL + "/api/v2/networkshaping/stop";
    public static final String RELEASE_DEVICE = BASE_URL + "/api/release_device"; //Manual session release device
    
    //Automation API Endpoints
    public static final String USER_INFO = BASE_URL + "/api/v2/user/info";
    public static final String APPIUM_INITV2 = BASE_URL + "/api/v2/booking/automation/appium/init";
    public static final String APPIUM_INIT = BASE_URL + "/api/appium/init";
    public static final String INIT_LIVEVIEW = BASE_URL +  "/api/v2/generic/init-liveview";
    public static final String UNLOCK_DEVICE = BASE_URL + "/api/v2/unlock-device";
    public static final String INSTALL_APP_AUTOMATION = BASE_URL + "/api/install_app";
    public static final String LONELY_APPIUM = BASE_URL + "/api/lonelyappium";
    public static final String CAPTURE_SCREENSHOT = BASE_URL + "/api/capture_device_screenshot";
    public static final String GET_PLAYER = BASE_URL + "/api/v2/get-player";
    public static final String INSTALL_APP_ENTERPRISE = BASE_URL + "/api/v2/install-app";
    public static final String START_DEVICESERVICE = BASE_URL + "/api/startdeviceservices";
    public static final String GET_APPIUMFILELIST = BASE_URL + "/api/appium/get_appium_file_list";
    public static final String DOWNLOAD_APPIUMACESSDATA = BASE_URL + "/api/appium/download_appium_access_data";
    public static final String APPIUM_ENDPOINT = BASE_URL + "/api/appium/endpoint";
    public static final String APPIUM_FOLDER = BASE_URL + "/api/appium/folder";
    public static final String GET_SHAREABLEREPORTLINK = BASE_URL + "/api/appium/getShareableReportLink";
    public static final String DOWNLOAD_MANUALACCESSDATA = BASE_URL + "/api/download_manual_access_data";
    public static final String DOWNLOAD_FILE = BASE_URL + "/api/download_file";
    public static final String GET_APPIUMVERSION = BASE_URL + "/api/appium/get_available_appium_version";
    public static final String RELEASE_DEVICEAUTOMATION = BASE_URL + "/api/appium/update_session";
   
    //XCUITest API Endpoints
    
    public static final String BOOK_DEVICE_XCTEST = BASE_URL + "/api/automationbooking";
    public static final String XCTEST_INITV2 = BASE_URL + "/api/v2/initautomation";
    public static final String START_DEVICESERVICEXCTEST = BASE_URL + "/api/startdeviceservices";
    public static final String RELEASE_XCTESTDEVICE = BASE_URL + "/api/automationrelease";
    public static final String XCTESTSHREABLE_REPORTLINK = BASE_URL + "/api/getAutomationShareableReportLink";
    
    //App Instrumentation
    public static final String INITIATE_APP_INSTRUMENTATION = BASE_URL + "/api/app_instrumentation/initiate";
    public static final String INSTRUMENTATION_PROGRESS = BASE_URL + "/api/app_instrumentation/progress";
    public static final String DOWNLOAD_INSTRUMENTATION_APK = BASE_URL + "/api/app_instrumentation/download-to-cloud-drive";
    
    //Biometric Authentication
    public static final String BIOMETRIC_AUTHENTICATION = BASE_URL + "/api/fingerprint_auth";
    
    //Image Injection
    public static final String IMAGE_INJECT = BASE_URL + "/api/imageInject";
    
    //IPA Resign
    public static final String INITIATE_RESIGN = BASE_URL + "/api/resign/initiate";
    public static final String RESIGN_PROGRESS = BASE_URL + "/api/resign/progress";
    public static final String RESIGN_DOWNLOAD = BASE_URL + "/api/resign/download";
    public static final String RESIGN_IPA = BASE_URL + "/api/rename";
    
    
}
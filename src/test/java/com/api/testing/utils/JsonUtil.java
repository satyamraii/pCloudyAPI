package com.api.testing.utils;

import io.restassured.path.json.JsonPath;

public class JsonUtil {
	
    public static JsonPath printFormattedJson(String rawResponse) {
        JsonPath json = new JsonPath(rawResponse);
        System.out.println("Response JSON:\n" + json.prettify());
        return json;
    }
}
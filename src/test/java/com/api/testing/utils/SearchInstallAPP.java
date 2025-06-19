package com.api.testing.utils;

import io.restassured.path.json.JsonPath;
import java.util.List;
import java.util.Map;

public class SearchInstallAPP {

    // Generic method to find a file by name from JSON response
    public static Map<String, Object> findFileByName(JsonPath json, String fileNameToFind) {
        List<Map<String, Object>> files = json.getList("result.files");

        return files.stream()
                .filter(file -> file.get("file").toString().equalsIgnoreCase(fileNameToFind))
                .findFirst()
                .orElse(null);
    }

    // Optional method to print file details
    public static void printFileDetails(Map<String, Object> file) {
        if (file != null) {
            System.out.println("✅ File Found:");
            System.out.println("File Name: " + file.get("file"));
            System.out.println("Size (KB): " + file.get("size_KB"));
            System.out.println("Uploaded Time (UTC): " + file.get("time_UTC"));
            System.out.println("Extension: " + file.get("extension"));
            System.out.println("Folder Path: " + file.get("appendFolderPath"));
        } else {
            System.out.println("❌ File not found.");
        }
    }
    
    
}

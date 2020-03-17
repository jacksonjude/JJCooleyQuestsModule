package com.jacksonjude.JJCooleyQuests.lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

public class JJCooleyProperties {
	public static Properties prop = new Properties();
	
	public static void loadProperties(){
		try {
			prop.load(new FileInputStream(JJCooleyConstants.PROPERTIES_FILE));
		} catch(FileNotFoundException e) {
			System.out.println(JJCooleyConstants.PROPERTIES_FILE + " does not exist!");
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public static String readProperties(String key) {
		String e = prop.getProperty(key);
		System.out.println("GET " + key + " - " + e + "\n");
		
		return e;
	}
	
	public static void setProperties(String key, String value) {
		prop.setProperty(key, value);
		System.out.println("SET " + key + " - " + value + "\n");
	}
	
	public static void storeProperties() {
		try {
			prop.store(new FileOutputStream(JJCooleyConstants.PROPERTIES_FILE), null);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}

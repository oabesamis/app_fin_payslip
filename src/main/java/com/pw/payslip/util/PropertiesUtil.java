package com.pw.payslip.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public Properties loadProperties() {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			ClassLoader classLoader = getClass().getClassLoader();
			input = new FileInputStream(classLoader.getResource("Application.properties").getFile());

			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return prop;
	}
}

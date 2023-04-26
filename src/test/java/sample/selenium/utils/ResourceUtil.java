package sample.selenium.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceUtil {

	private static final ClassLoader CLASS_LOADER;
	private static final InputStream INPUT_STREAM;
	private static final Properties PROPERTIES;

	private static final String BASE_URL = "base.url";
	private static final String BASE_URL_ENV = "BASE_URL";
	static {
		CLASS_LOADER = ResourceUtil.class.getClassLoader();
		INPUT_STREAM = CLASS_LOADER.getResourceAsStream("config/config.properties");
		PROPERTIES = new Properties();
		try {
			PROPERTIES.load(INPUT_STREAM);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ResourceUtil() {
		throw new RuntimeException("Instantiation not allowed");
	}

	public static String getBaseUrl() {
		String redisAuthFromConfig = PROPERTIES.getProperty(BASE_URL);
		return System.getProperty(BASE_URL_ENV, redisAuthFromConfig);
	}

}

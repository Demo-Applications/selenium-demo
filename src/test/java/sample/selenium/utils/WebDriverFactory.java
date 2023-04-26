package sample.selenium.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;

public class WebDriverFactory {

	public WebDriver getWebDriver(DriverManagerType driverType) {
		return WebDriverManager.getInstance(driverType).create();
	}
}

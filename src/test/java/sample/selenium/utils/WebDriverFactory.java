package sample.selenium.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;	
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class WebDriverFactory {

	public WebDriver getWebDriver(DriverManagerType driverType) {
		Capabilities capabilities = getBrowserOptions(driverType);
		WebDriver driver = WebDriverManager.getInstance(driverType).capabilities(capabilities).create();
		return driver;
	}

	public static Capabilities getBrowserOptions(DriverManagerType driverManagerType) {
		switch (driverManagerType) {
		case CHROME:
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--no-sandbox");
			return chromeOptions;
		case FIREFOX:
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			return firefoxOptions;
		case EDGE:
			EdgeOptions edgeOptions = new EdgeOptions();
			return edgeOptions;
		case IEXPLORER:
			InternetExplorerOptions explorerOptions = new InternetExplorerOptions();
			return explorerOptions;
		default:
			throw new IllegalArgumentException("Unsupported driver manager type: " + driverManagerType.toString());
		}
	}
}

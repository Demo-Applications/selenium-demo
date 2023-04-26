package sample.selenium.webpages;

import org.openqa.selenium.WebDriver;
import sample.selenium.utils.ResourceUtil;

public abstract class BasePage {

	protected static String CHROME_DRIVER_DEFAULT_URL = "data:,";
	
	protected WebDriver driver;
	protected String baseUrl;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.baseUrl = ResourceUtil.getBaseUrl();
	}

	protected abstract String getPageUrl();

}

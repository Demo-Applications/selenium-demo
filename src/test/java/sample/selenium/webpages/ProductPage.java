package sample.selenium.webpages;

import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductPage extends BasePage {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductPage.class);

	private String productConfigFile = "/config/selector/productPage.json";
	private String productPageUrl = null;

	public ProductPage(WebDriver driver) {
		super(driver);
		loadPageConfig();
	}

	protected void loadPageConfig() {
		String configContent = null;
		try {
			configContent = IOUtils.toString(this.getClass().getResourceAsStream(productConfigFile),
					Charset.defaultCharset());
		} catch (IOException e) {
			LOGGER.error("Error reading logging config from file {}", productConfigFile);
			throw new RuntimeException("Error reading Product page configuration file");
		}
		JSONObject config = new JSONObject(configContent);

		productPageUrl = config.getString("url");
		String currentDriverLoc = driver.getCurrentUrl();
		if (!StringUtils.isEmpty(currentDriverLoc)
				&& !StringUtils.equalsIgnoreCase(driver.getCurrentUrl(), productPageUrl)) {
			LOGGER.error("The driver is supposed to be in Product page {}, but currently at page {}", productPageUrl,
					currentDriverLoc);
			throw new RuntimeException(
					"Invalid state !!, the driver is supposed to be in Product page, but currently at page :"
							+ currentDriverLoc);
		}
	}

	@Override
	public String getPageUrl() {
		return productPageUrl;
	}

}

package sample.selenium.webpages;

import java.nio.charset.Charset;
import java.time.Duration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.selenium.utils.LocatorUtil;

public class LoginPage extends BasePage {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

	private String loginConfigFile = "/config/selector/loginPage.json";
	private String loginPageUrl = null;

	private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20), Duration.ofMillis(10));;

	private WebElement usernameElement;
	private WebElement passwordElement;
	private WebElement loginButtonElement;
	private WebElement errorContainerElement;

	private JSONObject usernameField;
	private JSONObject passwordField;
	private JSONObject loginButton;
	private JSONObject errorMessageContainer;

	public LoginPage(WebDriver driver) {
		super(driver);
		loadPageConfig();
	}

	public void loadPageConfig() {
		String configContent = null;
		try {
			configContent = IOUtils.toString(this.getClass().getResourceAsStream(loginConfigFile),
					Charset.defaultCharset());
		} catch (Exception e) {
			LOGGER.error("Error reading logging config from file {} : {}", loginConfigFile, e.getMessage());
			throw new RuntimeException("Error reading login page configuration file", e);
		}
		JSONObject config = new JSONObject(configContent);

		loginPageUrl = config.getString("url");
		String currentDriverLoc = driver.getCurrentUrl();
		if (!StringUtils.isEmpty(currentDriverLoc)
				&& !StringUtils.equalsIgnoreCase(driver.getCurrentUrl(), CHROME_DRIVER_DEFAULT_URL)
				&& !StringUtils.equalsIgnoreCase(driver.getCurrentUrl(), loginPageUrl)) {
			LOGGER.error("The driver is supposed to be in login page {}, but currently at page {}", loginPageUrl,
					currentDriverLoc);
			throw new RuntimeException(
					"Invalid state !!, the driver is supposed to be in login page, but currently at page :"
							+ currentDriverLoc);
		}

		usernameField = config.getJSONObject("usernameField");
		passwordField = config.getJSONObject("passwordField");
		loginButton = config.getJSONObject("loginButton");
		errorMessageContainer = config.getJSONObject("errorMessageContainer");

		LOGGER.debug("Login page initialized successfully");

	}

	public void open() {
		driver.get(loginPageUrl);
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
				.equals("complete"));
		loadElements();

	}

	private void loadElements() {
		usernameElement = wait.until(ExpectedConditions
				.presenceOfElementLocated(LocatorUtil.getLocator(usernameField.getString("type"), usernameField.getString("value"))));

		passwordElement = wait.until(ExpectedConditions
				.presenceOfElementLocated(LocatorUtil.getLocator(passwordField.getString("type"), passwordField.getString("value"))));

		loginButtonElement = wait.until(ExpectedConditions
				.presenceOfElementLocated(LocatorUtil.getLocator(loginButton.getString("type"), loginButton.getString("value"))));

	}

	// The login page allows the user to type their username
	public LoginPage typeUsername(String username) {
		usernameElement.sendKeys(username);
		// Return the current page object as this action doesn't navigate to a page
		// represented by another PageObject
		return this;
	}

	// The login page allows the user to type their password into the password field
	public LoginPage typePassword(String password) {
		passwordElement.sendKeys(password);
		// Return the current page object as this action doesn't navigate to a page
		// represented by another PageObject
		return this;
	}

	// The login page allows the user to submit the login form
	public ProductPage submitLogin() {
		// Redirects to be the product page.
		// A separate method should be created for the instance of clicking login whilst
		// expecting a login failure.
		loginButtonElement.submit();
		return new ProductPage(driver);
	}

	// The login page allows the user to submit the login form knowing that an
	// invalid username and / or password were entered
	public LoginPage submitLoginExpectingFailure() {
		// This is the only place that submits the login form and expects the
		// destination to be the login page due to login failure.
		loginButtonElement.submit();
		
		errorContainerElement = driver
				.findElement(LocatorUtil.getLocator(errorMessageContainer.getString("type"), errorMessageContainer.getString("value")));

		// Return a new page object representing the destination. Should the user ever
		// be navigated to the home page after submitting a login with credentials
		// expected to fail login, the script will fail when it attempts to instantiate
		// the LoginPage PageObject.
		return this;
	}

	// Conceptually, the login page offers the user the service of being able to
	// "log into"
	// the application using a user name and password.
	public ProductPage loginAs(String username, String password) {
		typeUsername(username);
		typePassword(password);
		return submitLogin();
	}

	public String getLoginErrorMessage() {
		return errorContainerElement.getText();
	}

	@Override
	public String getPageUrl() {
		return this.loginPageUrl;
	}

}
package sample.selenium.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import java.time.LocalDateTime;
import java.util.Map;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import sample.selenium.utils.WebDriverFactory;
import sample.selenium.webpages.LoginPage;
import sample.selenium.webpages.ProductPage;

public class LoginStep {

	private LoginPage loginPage;
	private ProductPage productPage;
	private WebDriver driver;


	@Given("User opens a Web Borwser")
	public void user_opens_a_web_borwser() {
		driver = new WebDriverFactory().getWebDriver(DriverManagerType.CHROME);
		loginPage = new LoginPage(driver);
	}

	@Given("User is on the login page")
	public void user_is_on_the_login_page() {
		loginPage.open(); 
	}

	@Given("User inputs name {string} and password {string} and submits")
	public void user_inputs_name_and_password_and_submits(String userName, String password) {
		productPage = loginPage.loginAs(userName, password);
	}

	@When("User inputs credentials:")
	public void user_inputs_credentials(DataTable credentialsTable) {
		Map<String, String> credentials = credentialsTable.asMap();
		String userName = credentials.get("user_name");
		String password = credentials.get("password");
		loginPage.typeUsername(userName);
		loginPage.typePassword(password);
		loginPage = loginPage.submitLoginExpectingFailure();
	}

	@Then("User should not be able to login")
	public void user_should_not_be_able_to_login() {
		Assert.assertTrue(
				loginPage.getLoginErrorMessage().toLowerCase().contains("username and password do not match"));
	}

	@Then("User should be able to login successfully")
	public void user_should_be_able_to_login_successfully() {
		Assert.assertEquals(productPage.getPageUrl(), driver.getCurrentUrl());
	}

	@After
	public void takeScreenshot(Scenario scenario) {
		if (scenario.isFailed()) {
			byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshotBytes, "image/png",
					String.format("Screenshot-%s-%s.png",
							scenario.getName().toLowerCase().replaceAll("\\s", "-"), LocalDateTime.now()));
		}
		
		driver.close();
	}
}

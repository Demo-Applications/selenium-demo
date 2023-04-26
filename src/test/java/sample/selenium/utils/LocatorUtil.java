package sample.selenium.utils;

import org.openqa.selenium.By;

public class LocatorUtil {

	private LocatorUtil() {
		throw new RuntimeException("Instantiation not allowed");
	}

	public static By getLocator(String locatorType, String locator) {
		By by;
		locatorType = locatorType.trim();
		switch (locatorType) {
		case "id":
			by = By.id(locator);
			break;
		case "name":
			by = By.name(locator);
			break;
		case "className":
			by = By.className(locator);
			break;
		case "tagName":
			by = By.tagName(locator);
			break;
		case "linkText":
			by = By.linkText(locator);
			break;
		case "partialLinkText":
			by = By.partialLinkText(locator);
			break;
		case "cssSelector":
			by = By.cssSelector(locator);
			break;
		case "xpath":
			by = By.xpath(locator);
			break;
		default:
			throw new IllegalArgumentException("Invalid locator type: " + locatorType);
		}
		return by;
	}
}

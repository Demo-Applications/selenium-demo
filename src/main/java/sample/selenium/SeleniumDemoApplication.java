package sample.selenium;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Redis test using Jedis
 *
 */
public class SeleniumDemoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumDemoApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Application started. Total {} arguments received : {}", args.length, Arrays.asList(args));

	}
}

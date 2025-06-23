package com.nse.automation.driver;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.nse.automation.exceptions.FrameworkExceptions;
import com.nse.automation.report.LoggerManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSetUp {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static Map<String, String> configValues = null;
	public static boolean browserInitialized = false;

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static boolean initializeDriver(String browser, String appUrl) throws FrameworkExceptions {
		
		WebDriver webDriver = null;
		
		
		switch (browser.toLowerCase()) { // Use toLowerCase for case-insensitivity
		case "chrome":
			WebDriverManager.chromedriver().setup();
			webDriver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			webDriver = new FirefoxDriver();
			break;
		case "edge": // Add Edge
			WebDriverManager.edgedriver().setup();
			webDriver = new EdgeDriver();
			break;
		// Add more browsers as needed (e.g., "safari", "ie")
		default:
			throw new IllegalArgumentException("Browser '" + browser + "' is not supported.");
		}
			
		// Add more browsers as needed
		driver.set(webDriver);
		driver.get().manage().window().maximize();
		driver.get().get(appUrl);
		LoggerManager.logInfo("Driver initialized for browser: " + browser);
		// Add implicit waits or page load timeouts here
		browserInitialized=true;
		return true;
	}

	public static void closeDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

	/**
	 * Gets the config property.
	 *
	 * @param configName the config name
	 * @return the config property
	 * @throws Exception the exception
	 */
	public static String getConfigProperty(String configName) throws FrameworkExceptions {
		LoggerManager.trace("Try to fetch the content form Configuration file with key" + configName);
		String configValue = configValues.get(configName.toUpperCase());
		LoggerManager.trace("Fetching the value from the Configuration file with key '" + configName + "' and the result is '"
				+ configValue + "'");
		if (null == configValue) {
			LoggerManager.error("The specified '" + configName + "'configuration property is not available");
			throw new FrameworkExceptions("The required config value '" + configName + "' is not available");
		}
		return configValue;
	}
}

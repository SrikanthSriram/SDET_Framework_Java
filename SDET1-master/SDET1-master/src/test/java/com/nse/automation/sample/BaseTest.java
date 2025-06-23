package com.nse.automation.sample;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.nse.automation.driver.DriverSetUp;
import com.nse.automation.exceptions.FrameworkExceptions;
import com.nse.automation.report.LoggerManager;
import com.nse.automation.utils.Assertions;
import com.nse.automation.utils.CommonUtils;
import com.nse.automation.utils.Config;
import com.nse.automation.utils.Waits;

public class BaseTest {

	protected Assertions Assertions;
	private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
	protected String baseURL;

	public WebDriver getDriver() {
		return driverThread.get();
	}

	@BeforeClass(alwaysRun = true)
	public void init() {
		Assertions = new Assertions();
		LoggerManager.info("================================ TEST STARTS NOW  =========================================");
	}

	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method) {
		try {
			String browser = System.getenv("BROWSER");
			if (browser == null || browser.equalsIgnoreCase("null")) {
				browser = Config.getConfigProperty("BrowserType");
			}

			baseURL = Config.getConfigProperty("AppURL");

			boolean isDriverInitialized = DriverSetUp.initializeDriver(browser, baseURL);
			if (!isDriverInitialized) {
				throw new FrameworkExceptions("Driver initialization failed for browser: " + browser);
			}

			WebDriver driver = DriverSetUp.getDriver();
			driverThread.set(driver);

			gotoUrl(baseURL);
			Waits.implicitWait();

			LoggerManager.info(baseURL);
			LoggerManager.info("========================== TEST METHOD STARTS ============================");
			LoggerManager.info("======== " + getClass().getSimpleName() + ": " + method.getName() + " =========");

		} catch (Exception e) {
			LoggerManager.logError("Error in setUp: " + e.getMessage());
		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(Method method, ITestResult result, ITestContext ctx) {
		WebDriver driver = getDriver();
		try {
			String suiteName = ctx.getCurrentXmlTest().getSuite().getName();

			if (!result.isSuccess()) {
				new CommonUtils().takeScreenShot(suiteName, method);
			}

			if (driver != null) {
				driver.quit();
				LoggerManager.logInfo("Closed the browser for thread: " + Thread.currentThread().getId());
			}

			driverThread.remove();

			LoggerManager.info("================================ TEST METHOD ENDS  =========================================");

		} catch (Exception e) {
			if (driver != null) {
				driver.quit();
			}
			LoggerManager.logError("Failed to close browser for thread: " + Thread.currentThread().getId() + ", " + e.getMessage());
		}
	}

	@AfterClass(alwaysRun = true)
	public void logoutAndCloseOpenedBrowsers() {
		WebDriver driver = getDriver();
		try {
			if (driver != null) {
				driver.quit();
			}
			driverThread.remove();
		} catch (Exception e) {
			LoggerManager.logError("Error in @AfterClass: " + e.getMessage());
		}
	}

	protected void gotoUrl(String url) throws FrameworkExceptions {
		getDriver().get(url);
	}
}

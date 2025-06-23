package com.nse.automation.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.nse.automation.driver.DriverSetUp;
import com.nse.automation.exceptions.FrameworkExceptions;
import com.nse.automation.report.LoggerManager;

public class CommonUtils {
	protected WebDriver driver = DriverSetUp.getDriver();

	public Robot getRobot() throws AWTException {
		return new Robot();
	}

	private WebDriver getDriver() {
		return DriverSetUp.getDriver();
	}

	public Actions getAction() {
		return new Actions(driver);
	}

	public String getAlertText() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
		return alert.getText();
	}

	/**
	 * To capture the screen shot based on user need.
	 * 
	 * @param suiteName
	 * @param method
	 * @throws IOException
	 * @throws FrameworkExceptions
	 */
	public void takeScreenShot(String suiteName, Method method) throws IOException, FrameworkExceptions {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source, new File(System.getProperty("user.dir") + "\\test-output\\teardown_scrnshots\\"
				+ suiteName + "_" + method.getName() + ".png"));
		LoggerManager.info("Screenshot taken for failed method " + method.getName());
	}

}


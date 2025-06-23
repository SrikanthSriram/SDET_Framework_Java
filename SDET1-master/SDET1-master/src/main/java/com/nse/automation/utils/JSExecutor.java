package com.nse.automation.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.nse.automation.driver.DriverSetUp;
import com.nse.automation.exceptions.FrameworkExceptions;

public class JSExecutor {
	private JSExecutor() {
	}

	public static JavascriptExecutor getJavaScriptExec() {
		return (JavascriptExecutor) DriverSetUp.getDriver();
	}

	public static void scrollPage(WebElement element) throws FrameworkExceptions {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) DriverSetUp.getDriver();
		jsExecutor.executeScript("arguments[0].scrollIntoView();", element);
	}

	public static void setValue(WebElement ipLabelName1, String username) {
		JavascriptExecutor js = (JavascriptExecutor) DriverSetUp.getDriver();
		js.executeScript("arguments[0].value = '" + username + "';", ipLabelName1);
	}
}


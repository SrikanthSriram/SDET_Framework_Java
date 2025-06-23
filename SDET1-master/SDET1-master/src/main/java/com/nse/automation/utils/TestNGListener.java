package com.nse.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.apache.logging.log4j.Logger;

import com.nse.automation.driver.DriverSetUp;
import com.nse.automation.report.LoggerManager;

import io.qameta.allure.Attachment;
import junit.framework.TestListener;

public class TestNGListener extends TestListenerAdapter {// implements ITestListener extends TestListenerAdapter
Assertions assertions = new Assertions();
	private static final Logger logger = LoggerManager.getLogger();

	
	
	@Attachment(value = "Screenshot on Failure", type = "image/png")
	public byte[] captureScreenshot(WebDriver d) {
		return ((TakesScreenshot) d).getScreenshotAs(OutputType.BYTES);
	}
	    @Override
	    // Capture the Screen shot on Failure
	    public void onTestFailure(ITestResult tr) {
	    	WebDriver driver = DriverSetUp.getDriver();
	        	assertions.logInformationWithScreenShot("Screenshot captured for test case:" + tr.getMethod().getMethodName());
	            captureScreenshot(driver);
	        }
	    }
	    
		
		

    

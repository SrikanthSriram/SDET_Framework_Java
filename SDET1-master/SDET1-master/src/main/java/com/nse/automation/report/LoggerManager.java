package com.nse.automation.report;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.nse.automation.driver.DriverSetUp;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

public class LoggerManager {
	
	private LoggerManager() {
	}

	/**
	 * This method is used to get the logger instance.
	 * 
	 * @return Logger instance
	 */
	
	public static Logger getLogger() {
        return LogManager.getLogger();
    }

	public static final void fatal(String message) {
		getLogger().fatal(message);
		//getLogger(Logger1.class).fatal(message);
	}

	public static final void fatal(String message, Throwable t) {
		getLogger().fatal(message, t);
	}

	public static final void error(String message) {
		getLogger().error(message);
	}

	public static final void error(String message, Throwable t) {
		getLogger().error(message, t);
	}

	public static final void debug(String message, Throwable t) {
		getLogger().debug(message, t);
	}

	public static final void warn(String message, Throwable t) {
		getLogger().warn(message, t);
	}

	public static final void trace(String message) {
		getLogger().trace(message);
	}

	public static final void trace(String message, Throwable t) {
		getLogger().trace(message, t);
	}

	public static final void info(String message, Throwable t) {
		getLogger().info(message, t);
	}

	public static final void warn(String message) {
		getLogger().warn(message);
	}

	public static final void info(String message) {
		getLogger().info(message);
	}

	public static final void debug(String message) {
		getLogger().debug(message);
	}

	@Step("{0}")
	public static void logInfo(String stepDescription) {
		getLogger().info(stepDescription);
	}
	
	@Step("{0}")
	public static final void logError(String message) {
		getLogger().error(message);
		captureScreenshot();
	}
	
	@Step("{0}")
	public static final void logError(String message, Throwable t) {
		getLogger().error(message, t);
		captureScreenshot();
	}

	@Step("{0}")
	public static void logInfowitSS(String stepDescription) {
		getLogger().info(stepDescription);
		captureScreenshot();
	}
	@Step("{0}")
	public static final void logWarn(String message, Throwable t) {
		getLogger().warn(message, t);
	}
	@Step("{0}")
	public static final void logWarn(String message) {
		getLogger().warn(message);
	}


	 @Attachment
	public static byte[] captureScreenshot() {
		return ((TakesScreenshot) DriverSetUp.getDriver()).getScreenshotAs(OutputType.BYTES);
	}
}

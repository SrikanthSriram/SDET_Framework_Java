package com.nse.automation.imp;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.nse.automation.config.Constants;
import com.nse.automation.driver.DriverSetUp;
import com.nse.automation.exceptions.FrameworkExceptions;
import com.nse.automation.report.LoggerManager;
import com.nse.automation.ui.IElement;
import com.nse.automation.utils.Config;
import com.nse.automation.utils.JSExecutor;
import com.nse.automation.utils.Waits;

public class Element implements IElement {

	protected WebElement element;
	public String elementOriginalName = "";
	protected WebDriver driver;
	

	@Override
	public WebElement getElement() {
		return element;
	}
	
	/*
	 * public Element(List<WebElement> elements) { this.elements = elements;
	 * getInitializedDriver(); }
	 * 
	 * public Element(List<WebElement> elements, String elementName) { this.elements
	 * = elements; elementOriginalName = elementName; getInitializedDriver(); }
	 */

	public Element() {
		getInitializedDriver();
	}

	public Element(WebElement element) {
		this.element = element;
		getInitializedDriver();
	}

	public Element(WebElement element, String elementName) {
		this.element = element;
		elementOriginalName = elementName;
		getInitializedDriver();
	}

	// Get the initialized Driver
	private void getInitializedDriver() {
		driver = DriverSetUp.getDriver();
	}
	
	@Override
	public boolean isDisplayed() throws FrameworkExceptions {
		try {
			Waits.explicitWait(2);
			if (Waits.fluentWait(element, 5) != null) {
				element.isDisplayed();
				return true;
			} else {
				return false;
			}

		} catch (Exception fe) {
			LoggerManager.logError(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " isDisplayed() of Element class");
			return false;
		}

	}

	@Override
	public boolean clickElement() throws FrameworkExceptions {
		try {
			if (isLoaded()) {
				element.click();
				return true;
			} else {
				LoggerManager.logError(Constants.FORMATTER + Constants.ELEMENTLOGMESSAGE + elementOriginalName
						+ Constants.ISLOADEDLOGMESSAGE_FAILURE);
				return false;
			}
		} catch (Exception fe) {
			LoggerManager.logError(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getText() of Element class");
			return false;
		}
	}

	// Click on Element using Java Script
	@Override
	public boolean jsClick() {
		try {
			if (isLoaded()) {
				JSExecutor.getJavaScriptExec().executeScript("arguments[0].click();", element);
				return true;
			} else {
				LoggerManager.logError(Constants.FORMATTER + Constants.ELEMENTLOGMESSAGE + elementOriginalName
						+ Constants.ISLOADEDLOGMESSAGE_FAILURE);
				return false;
			}
		} catch (Exception fe) {
			LoggerManager.logError(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getText() of Element class");
			return false;
		}
	}

	@Override
	public boolean isLoaded() throws FrameworkExceptions {
		try {
			boolean flag = false;
			if (Waits.fluentWait(element,
					Integer.parseInt(Config.getConfigProperty(Constants.ELEMENTWAITTIME))) != null) {
				flag = true;
				LoggerManager.info(Constants.ELEMENTLOGMESSAGE + elementOriginalName + " loaded");
			} else {
				flag = false;
			}
			Waits.explicitWait(1);
			return flag;
		} catch (FrameworkExceptions ex) {
			LoggerManager.info(ex.getMessage() + "  isLoaded() ");
			LoggerManager.logError(Constants.FORMATTER + Constants.ELEMENTLOGMESSAGE + elementOriginalName
					+ Constants.ISLOADEDLOGMESSAGE_FAILURE);
			throw new FrameworkExceptions("Element: " + elementOriginalName + " not loaded" + ex);
		}
	}

	@Override
	public String getAttribute(String value) throws FrameworkExceptions {
		try {
			return element.getAttribute(value);
		} catch (Exception fe) {
			LoggerManager
					.logError(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getAttribute() of Element class");
			throw new FrameworkExceptions(
					Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getAttribute() of Element class." + fe);
		}
	}

	@Override
	public String getText() throws FrameworkExceptions {
		try {
			// if (isLoaded()) {
			String text = null;
			if (getAttribute("value") != null)
				text = getAttribute("value");
			else if (getAttribute("text") != null)
				text = getAttribute("text");
			else if (getAttribute("textContent") != null)
				text = getAttribute("textContent");
			else if (getAttribute("innerText") != null)
				text = getAttribute("innerText");
			LoggerManager.info(Constants.ELEMENTLOGMESSAGE + elementOriginalName + " get text successfully");
			return text;
			/*
			 * } else { Logger.logError(Constants.FORMATTER + Constants.ELEMENTLOGMESSAGE +
			 * elementOriginalName + " failed to get text"); throw new
			 * FrameworkExceptions("Element Element: " + elementOriginalName +
			 * " not loaded in method getText()"); }
			 */
		} catch (Exception fe) {
			LoggerManager.logError(Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getText() of Element class");
			throw new FrameworkExceptions(
					Constants.FORMATTER + Constants.FAILURE_METHOD_MESSAGE + " getText() of Element class." + fe);
		}
	}

}

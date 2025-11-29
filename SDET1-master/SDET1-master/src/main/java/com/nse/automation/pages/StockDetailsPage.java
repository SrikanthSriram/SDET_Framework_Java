package com.nse.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.nse.automation.driver.DriverSetUp;
import com.nse.automation.exceptions.FrameworkExceptions;
import com.nse.automation.imp.Element;
import com.nse.automation.pageclass.Components;
import com.nse.automation.report.LoggerManager;
import com.nse.automation.ui.IElement;
import com.nse.automation.utils.Waits;

public class StockDetailsPage extends Components {

	private WebDriver getDriver() {
		return DriverSetUp.getDriver();
	}

	@FindBy(className = "img-fluid main_logo")
	private WebElement nseLogo;

	@FindBy(xpath = "//a[@class='activeTab']")
	private WebElement stockName;

	@FindBy(xpath = "//div[contains(@class, 'symbol_high')]/following-sibling::div")
	private WebElement week52HighValue;

	@FindBy(xpath = "//div[contains(@class, 'symbol_low')]/following-sibling::div")
	private WebElement week52LowValue;

	private IElement getNSELogo() {
		return getComponent(nseLogo, Element.class, this.getClass());
	}

	private IElement getStockName() {
		return getComponent(stockName, Element.class, this.getClass());
	}

	private IElement getWeek52HighValue() {
		return getComponent(week52HighValue, Element.class, this.getClass());
	}

	private IElement getWeek52LowValue() {
		return getComponent(week52LowValue, Element.class, this.getClass());
	}

	public void verifyNSELogo() throws FrameworkExceptions {
		Waits.waitForPageLoadJS();
		if (getNSELogo().isDisplayed()) {
			LoggerManager.logInfo("NSE Logo is displayed");
		} else {
			LoggerManager.logError("NSE Logo is not displayed");
		}
	}

	public void verifyStockName(String expectedStockName) throws FrameworkExceptions {
		String actualStockName = getStockName().getText();
		if (actualStockName.equals(expectedStockName)) {
			Assert.assertEquals(actualStockName, expectedStockName, "Stock name is not displayed correctly");
			LoggerManager.logInfo("Stock name is displayed correctly: " + actualStockName);
		} else {
			LoggerManager.logError("Stock name is not displayed correctly. Expected: " + expectedStockName
					+ ", Actual: " + actualStockName);
			Assert.fail("Stock name is not displayed correctly");
		}
	}

	public void verify52WeekHighValue(String expectedValue) throws FrameworkExceptions {
		String actualValue = getWeek52HighValue().getText();
		if (actualValue.equals(expectedValue)) {
			Assert.assertEquals(actualValue, expectedValue, "52 Week High value is not displayed correctly");
			LoggerManager.logInfo("52 Week High value is displayed correctly: " + actualValue);
		} else {
			LoggerManager.logError("52 Week High value is not displayed correctly. Expected: " + expectedValue
					+ ", Actual: " + actualValue);
			Assert.fail("52 Week High value is not displayed correctly");
		}
	}

	public void verify52WeekLowValue(String expectedValue) throws FrameworkExceptions {
		String actualValue = getWeek52LowValue().getText();
		if (actualValue.equals(expectedValue)) {
			Assert.assertEquals(actualValue, expectedValue, "52 Week Low value is not displayed correctly");
			LoggerManager.logInfo("52 Week Low value is displayed correctly: " + actualValue);
		} else {
			LoggerManager.logError("52 Week Low value is not displayed correctly. Expected: " + expectedValue
					+ ", Actual: " + actualValue);
			Assert.fail("52 Week Low value is not displayed correctly");
		}
	}

	public void printStockDetails() {

		LoggerManager.logInfowitSS("Stock Name: " + stockName.getText());
		LoggerManager.logInfowitSS("52 Week High Value: " + week52HighValue.getText());
		LoggerManager.logInfowitSS("52 Week Low Value: " + week52LowValue.getText());
	}
}

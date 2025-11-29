package com.nse.automation.pages;

import org.openqa.selenium.By;
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
import com.nse.automation.utils.Assertions;
import com.nse.automation.utils.Waits;

public class HomePage extends Components {

	private WebDriver getDriver() {
		return DriverSetUp.getDriver();
	}

	/*
	 * public HomePage(WebDriver driver) {
	 * 
	 * this.driver = driver; }
	 */

	/**
	 * locators
	 */
	@FindBy(className = "main_menu")
	private WebElement homeBanner;

	@FindBy(xpath = "//div[@class='marquee marquee_section']")
	private WebElement marqueeSection;
	
	String marqueeTextElement= "//div[@class='marquee-content playing']/a/div[text()='%stokName']";
	
	
	
	
	
	private IElement getHomeBannerEle() {return getComponent(homeBanner,Element.class, this.getClass());}
	private IElement getmarqueeSectionEle() {return getComponent(marqueeSection,Element.class, this.getClass());}
	/*
	 * private IElement getMarqueeTextEle(String marqueeText) { if (marqueeText ==
	 * null || marqueeText.isEmpty()) {
	 * LoggerManager.error("Marquee text cannot be null or empty"); throw new
	 * IllegalArgumentException("Marquee text cannot be null or empty"); }
	 * WebElement stock =
	 * getDriver().findElement(By.xpath(marqueeTextElement.replace("%stokName",
	 * marqueeText))); return getComponent(stock, Element.class, this.getClass()); }
	 */
	
	/*
	 * IElement homeBannerElement = getComponent(homeBanner,Element.class,
	 * this.getClass()); IElement marqueeSectionElement =
	 * getComponent(marqueeSection, Element.class, this.getClass());
	 */



	public void verifyTheNSEPageLoaded() throws FrameworkExceptions {
		Waits.waitForPageLoadJS();
		LoggerManager.logInfo("Page loaded successfully");				
		Assert.assertTrue(getHomeBannerEle().isDisplayed(), "Home Banner is not displayed");
		LoggerManager.logInfo("Home Banner is displayed");
	}

	public void verifyMarqueeSection() throws FrameworkExceptions {
		Assert.assertTrue(getmarqueeSectionEle().isDisplayed(), "Marquee Section is not displayed");
		LoggerManager.logInfo("Marquee Section is displayed");
	}

	public void clickOnMarqueeSection(String stockName) {
		
  		if (stockName == null || stockName.isEmpty()) {
			LoggerManager.logError("Marquee text cannot be null or empty");
			throw new IllegalArgumentException("Marquee text cannot be null or empty");
		}
		LoggerManager.logInfo("Clicked on Marquee Section with text: " + stockName);
		Waits.waitForPageLoadJS();
	}
	
	public void clickOnStockfomtheMarquee(String stockName) {
		if (stockName == null || stockName.isEmpty()) {
			LoggerManager.logError("Stock name cannot be null or empty");
			throw new IllegalArgumentException("Stock name cannot be null or empty");
		}
		
		
		WebElement ele=getDriver().findElement(By.xpath("//div[@class='marquee-content playing']/a/div[text()='"+stockName+"']"));
		Waits.waitUntilElementToBeVisible(ele, 120);
		IElement stock=getComponent(ele, Element.class, this.getClass());
		stock.jsClick();
		LoggerManager.logInfowitSS("Clicked on Stock from the Marquee with text: " + stockName);
		Waits.waitForPageLoadJS();
	}
}

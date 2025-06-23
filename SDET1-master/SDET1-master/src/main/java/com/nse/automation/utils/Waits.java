package com.nse.automation.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.nse.automation.config.Constants;
import com.nse.automation.driver.DriverSetUp;
import com.nse.automation.exceptions.FrameworkExceptions;
import com.nse.automation.report.LoggerManager;

public class Waits {
	public static final long defaultTimeout = 5000;
	private static int waitTime = 0;
	private static WebDriver driver = DriverSetUp.getDriver();

	private Waits() {
	}

// Fluent Wait
	public static WebElement fluentWait(final WebElement element, long duration) {
		try {
			return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(duration))
					.pollingEvery(Duration.ofSeconds(1))
					.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
					.until(new Function<WebDriver, WebElement>() {
						@Override
						public WebElement apply(WebDriver input) {
							boolean isPresent = element.isDisplayed() && element.isEnabled();
							if (isPresent) {
								return element;
							} else {
								return null;
							}
						}
					});
		} catch (Exception e) {
			LoggerManager.info("Exception at fluentWait() :Element- " + element + " not found" + e);
			return null;
		}
	}

// Wait for Angular Load

// Wait for the Page to load
	public static boolean waitForPageLoadJS() {
		try {

			new WebDriverWait(driver, Duration.ofSeconds(getWaitTime())).until(new Function<WebDriver, Boolean>() {
				public Boolean apply(WebDriver driver) {
					return ("complete")
							.equals(JSExecutor.getJavaScriptExec().executeScript("return document.readyState"));
				}
			});
			return ("complete").equals(JSExecutor.getJavaScriptExec().executeScript("return document.readyState"));
		} catch (Exception e) {
			LoggerManager.logError("Page did not load");
			return false;
		}

	}

// Get Wait Time
	public static int getWaitTime() {
		if (waitTime == 0)
			waitTime = Constants.ELEMENT_WAITTIME;
		return waitTime;
	}

	public static boolean waitForElementValueToLoad(final WebElement element, final String strType,
			final String strAttribute) {
		boolean blnStatus = false;
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
					.pollingEvery(Duration.ofSeconds(5));
			Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
				public Boolean apply(WebDriver driver) {
					switch (strType) {
					case "ATTRIBUTE":
						if (!element.getDomAttribute(strAttribute).isEmpty())
							return true;
						break;
					case "ISDISPLAYED":
						if (element.isDisplayed())
							return true;
						break;

					default:
						LoggerManager.info("In Default");
					}
					return false;
				}
			};
			blnStatus = wait.until(function);
		} catch (Exception e) {
			LoggerManager.info("Exception occurred in waitForElementValueToLoad method");
		}
		return blnStatus;
	}

	public static boolean waitUntilElementToBeVisible(WebElement element, long durationInSec) {
		boolean blnStatus = false;
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(durationInSec)).pollingEvery(Duration.ofSeconds(5));
			Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
				public Boolean apply(WebDriver driver) {
					if (element.isDisplayed())
						return true;
					return false;
				}
			};
			blnStatus = wait.until(function);
		} catch (Exception e) {
			LoggerManager.info("Exception occurred in waitForElementValueToLoad method");
		}
		return blnStatus;
	}

	public static void explicitWait() {
		try {
			Thread.sleep(defaultTimeout);
		} catch (Exception e) {
			LoggerManager.error("Failed in explicitWait method" + e);
		}
	}

	public static void explicitWait(int waitTimeinSec) {
		try {
			Thread.sleep(waitTimeinSec * 1000);
		} catch (Exception e) {
			LoggerManager.error("Failed in explicitWait method" + e);
		}
	}

	public static WebElement componentWait(final WebElement element, long durationInSec) throws FrameworkExceptions {
		try {
			return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(durationInSec))
					.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
					.pollingEvery(Duration.ofSeconds(1)).until(new Function<WebDriver, WebElement>() {
						@Override
						public WebElement apply(WebDriver input) {
							boolean isPresent = element.isDisplayed() && (element.isEnabled()
									|| (element.getDomAttribute("readonly") != null
											&& (element.getDomAttribute("readonly").equals("true")
													|| element.getDomAttribute("readonly").equals("readonly")))
									|| (element.getDomAttribute("disabled") != null
											&& element.getDomAttribute("disabled").equals("true")));
							if (isPresent) {
								return element;
							} else {
								return null;
							}

						}
					});
		} catch (TimeoutException toe) {
			return null;
		} catch (Exception e) {
			LoggerManager.info("Element not found at componentWait() ");
			throw new FrameworkExceptions("Element: " + element + " not found" + e);
		}
	}

	/**
	 * Waits until the webelement invisible from visible
	 * 
	 * @param xpath, waitInSec
	 * @return
	 * @throws FrameworkExceptions
	 */
	public static boolean waitUntilInvisibleOfElement(List<WebElement> xpath, int waitInSec)
			throws FrameworkExceptions {
		boolean flag = false;
		try {
			// Waits.explicitWait(3);
			for (int i = 0; i < waitInSec; i++) {
				Waits.explicitWait(1);
				// List<WebElement> lst = driver.findElements(xpath);
				if (xpath.size() == 0) {
					flag = true;
					break;
				}
			}
			return flag;
		} catch (Exception e) {
			LoggerManager.info("Element not found at waitUntilInvisibleOfElement() ");
			return false;
		}
	}

	public static boolean waitUntilInvisibleOfElement(WebElement element, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.invisibilityOfAllElements(element));
	}

	public static void implicitWait() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));}
		//driver.manage().timeouts().;
	}



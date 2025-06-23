package com.nse.automation.ui;

import org.openqa.selenium.WebElement;

import com.nse.automation.exceptions.FrameworkExceptions;

public interface IElement {
	WebElement getElement();

	boolean isDisplayed() throws FrameworkExceptions;

	boolean clickElement() throws FrameworkExceptions;

	boolean jsClick();

	boolean isLoaded() throws FrameworkExceptions;

	String getAttribute(String value) throws FrameworkExceptions;

	String getText() throws FrameworkExceptions;
}

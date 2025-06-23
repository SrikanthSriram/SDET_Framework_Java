package com.nse.automation.exceptions;

import com.nse.automation.report.LoggerManager;

public class FrameworkExceptions extends Exception {

	final String errorMessage;

	public FrameworkExceptions(String message) {
		super(message);
		errorMessage = message;
	}

	/**
	 * Creates an instance of the class.
	 *
	 * @param page   the page
	 * @param object the object
	 */
	public FrameworkExceptions(String page, String object) {
		super();
		StringBuilder message = new StringBuilder();

		try {
			if ((page != null) && !("".equals(page)) && (object != null) && !("".equals(object))) {
				String key = page.toUpperCase() + " . " + object.toUpperCase();
				message.append(key);
			}
		} catch (Exception e) {
			message.append(e.getMessage());
			LoggerManager.error("Exception occurred in framework exception constructor ", e);
		}
		this.errorMessage = message.toString();
	}

	/**
	 * Instantiates a new framework exception.
	 * 
	 * @param
	 */
	public FrameworkExceptions(Throwable e) {
		super(e.getLocalizedMessage().split("\n")[0]);
		errorMessage = e.getMessage();

	}

	public FrameworkExceptions(String page, Throwable e) {
		super(page, e);
		errorMessage = e.getMessage();
	}
}

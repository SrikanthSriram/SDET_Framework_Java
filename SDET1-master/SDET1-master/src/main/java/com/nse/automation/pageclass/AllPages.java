package com.nse.automation.pageclass;

import org.openqa.selenium.support.PageFactory;

import com.nse.automation.driver.DriverSetUp;

public class AllPages {
	 private AllPages(){}

	    public static <T> T getPage(Class<T> clazz) {
	        return PageFactory.initElements(DriverSetUp.getDriver(), clazz);
	    }

}

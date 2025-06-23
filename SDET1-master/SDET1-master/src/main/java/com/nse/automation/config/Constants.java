package com.nse.automation.config;

import com.nse.automation.utils.FileUtility;

public class Constants {
	private Constants() {
	};

	public static final int ELEMENT_WAITTIME = 10;
	public static final String DOWNLOADDIR = "\\Downloads";
	

	public static final String CONFIGFILEPATH = FileUtility.getFileSeparatedPath("src/main/resources/Config.xml");
	public static final String FORMATTER = "#### ";
	public static final String ELEMENTLOGMESSAGE = "Element element: ";
	public static final String FAILURE_METHOD_MESSAGE = "Failure at the method: ";
	public static final String ISLOADEDLOGMESSAGE_FAILURE = " loading failed";
	public static final String SELECTLOGMESSAGE = " is Selected";
	public static final String SELECTLOGMESSAGE_FAILURE = " selection failed";
	public static final String TEXTFIELDLOGMESSAGE="TextField element: ";
	public static final String SETTEXTLOGMESSAGE = " set text successfully";
	public static final String TEXTFIELDCLEARTEXTMESSAGE = "Text field text is erased";
	public static final String ELEMENTWAITTIME ="ElementWaitTime";


	
/*
 * Data constants */
	
	public static final String STOCKNAME="BEL";
	public static final String WEEK52HIGH ="400.00";
	public static final String WEEK52LOW ="20.00";
}

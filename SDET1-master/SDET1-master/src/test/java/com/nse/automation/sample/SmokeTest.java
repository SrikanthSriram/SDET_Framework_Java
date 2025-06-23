package com.nse.automation.sample;

import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nse.automation.config.Constants;
import com.nse.automation.exceptions.FrameworkExceptions;
import com.nse.automation.pageclass.AllPages;
import com.nse.automation.pages.HomePage;
import com.nse.automation.pages.StockDetailsPage;
import com.nse.automation.utils.TestNGListener;

import io.qameta.allure.Owner;

@Listeners({ TestNGListener.class })
public class SmokeTest extends BaseTest {
	// HomePage homePage = new HomePage(driver);
	// StockDetailsPage stockDetailsPage=new StockDetailsPage(driver);
	private HomePage homePage() {
		return AllPages.getPage(HomePage.class);
	}

	private StockDetailsPage stockDetailsPage() {
		return AllPages.getPage(StockDetailsPage.class);
	}

	@Test(groups = { "smoke" }, description = "Verify the 52 Weeks stok high and low values")
	
	@Owner("Chowhan Praveen Kumar")
	public void VerifyThe52WeeksstokHighAndLowValues(ITestContext testContext) throws FrameworkExceptions {
		testContext.setAttribute("WebDriver", getDriver());
		homePage().verifyTheNSEPageLoaded();
		homePage().verifyMarqueeSection();
		homePage().clickOnStockfomtheMarquee("BEL");
		stockDetailsPage().verifyNSELogo();
		stockDetailsPage().verifyStockName("BEL");
		stockDetailsPage().verify52WeekHighValue(Constants.WEEK52HIGH);
		stockDetailsPage().verify52WeekLowValue(Constants.WEEK52LOW);
		stockDetailsPage().printStockDetails();
	}
	

	@Test(groups = { "smoke" }, description = "Verify the stock inffomation displayed")
	@Owner("Chowhan Praveen Kumar")
	public void testMethod2() throws FrameworkExceptions {
		homePage().verifyTheNSEPageLoaded();
		homePage().verifyMarqueeSection();
		homePage().clickOnStockfomtheMarquee(Constants.STOCKNAME);
		stockDetailsPage().verifyNSELogo();
		stockDetailsPage().verifyStockName(Constants.STOCKNAME);		
		stockDetailsPage().printStockDetails();
	}
	
	@Test(groups = { "smoke" }, description = "Print the stock details for BEL")
	@Owner("Chowhan Praveen Kumar")
	public void testMethod3() throws FrameworkExceptions {
		homePage().verifyTheNSEPageLoaded();
		homePage().verifyMarqueeSection();
		homePage().clickOnStockfomtheMarquee(Constants.STOCKNAME);
		stockDetailsPage().verifyNSELogo();
		stockDetailsPage().verifyStockName(Constants.STOCKNAME);		
		stockDetailsPage().printStockDetails();
	}
}

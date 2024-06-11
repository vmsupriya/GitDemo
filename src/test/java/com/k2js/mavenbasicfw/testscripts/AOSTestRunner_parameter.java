package com.k2js.mavenbasicfw.testscripts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.k2js.mavenbasicfw.pageobjects.Homepage;
import com.k2js.mavenbasicfw.pageobjects.Loginpage;
import com.k2js.mavenbasicfw.util.BrowserFactory;
import com.k2js.mavenbasicfw.util.CommonUtil;
import com.k2js.mavenbasicfw.util.SeleniumWaits;

public class AOSTestRunner_parameter {

	private WebDriver driver;
	private Homepage hp;
	String result = "fail";
	String errorMsg = "";
	String bn = null, rn = null, url = null;
	
	@Parameters({ "browser" ,"runmode"})

	@BeforeTest
	public void readData(@Optional("chrome") String bn,@Optional("local") String rn) {
		try {
			// step 1 read the browser
			//this.bn = CommonUtil.getPropertyvalue("config", "browserName");
			//System.out.println(bn);

			// step 2 read the runmode
			//this.rn = CommonUtil.getPropertyvalue("config", "runmode");
			//System.out.println(rn);
			
			this.bn=bn;
			System.out.println(bn);
			
			this.rn=rn;
			System.out.println(rn);

			// step 3 read the url
			this.url = CommonUtil.getPropertyvalue("config", "url");
			System.out.println(url);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@AfterMethod
	public void closure() {
		System.out.println(result);
		System.out.println(errorMsg);
		BrowserFactory.closeCurrentBrowserWindow();
	}

	@BeforeMethod
	public void preCondition() {

		result = "fail";
		errorMsg = "";

		// step 4
		this.driver = BrowserFactory.getBrowser(bn, rn);
		System.out.println(this.driver);

		// step 5
		BrowserFactory.launchApplication(url);

		// step 6
		this.hp = new Homepage(this.driver);
		System.out.println(hp);

	}

	@Test

	public void verifyHomePageTitle_TestCase1() throws Throwable {

		try {

			String expectedtitle = CommonUtil.getPropertyvalue("homepage", "title");
			System.out.println(expectedtitle);

			String actualtitle = hp.getActualTitle();
			System.out.println(actualtitle);

			Assert.assertEquals(actualtitle, expectedtitle);

			result = "pass";

		} catch (Throwable t) {

			CommonUtil.takeScreenshot(this.driver);
			errorMsg = t.getMessage();
			throw t;

		}

	}

	@Test
	public void verifyErrorMessageForWrongUserNameAndPassword_TestCase2() throws Throwable {

		try {

			hp.clickuserIcon();

			CommonUtil.sleepTime(SeleniumWaits.LONG_WAIT);
			Loginpage lp = new Loginpage(this.driver);

			// while calling take data from properties
			String un = CommonUtil.getPropertyvalue("loginpage", "username");
			lp.enterUserName(un);

			// while calling take data from properties
			String pass = CommonUtil.getPropertyvalue("loginpage", "password");
			lp.enterPassword(pass);

			lp.clickButton();
			CommonUtil.sleepTime(SeleniumWaits.SHORT_WAIT);

			String expErrMsg = CommonUtil.getPropertyvalue("loginpage", "errmsg");
			String actErrMsg = lp.getErrorMsg();

			Assert.assertEquals(actErrMsg, expErrMsg, "Error messages are not same");

			result = "pass";
		} catch (Throwable t) { // a parent class of exceptions can handle any of it's child exception.
			CommonUtil.takeScreenshot(this.driver);
			errorMsg = t.getMessage();
			throw t;

		}
	}

	@AfterTest
	public void closeBrowsers() {
		BrowserFactory.closeAllBrowsers();
	}
	
	@BeforeTest
	public void testWelcome() {
		System.out.println("Execution process startes");
	}
	
	@AfterTest
	public void testfarewell() {
		System.out.println("Execution process finishh");
	}
}
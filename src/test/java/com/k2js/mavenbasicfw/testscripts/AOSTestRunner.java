package com.k2js.mavenbasicfw.testscripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v85.browser.Browser;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.k2js.mavenbasicfw.pageobjects.Homepage;
import com.k2js.mavenbasicfw.pageobjects.Loginpage;
import com.k2js.mavenbasicfw.util.BrowserFactory;
import com.k2js.mavenbasicfw.util.CommonUtil;
import com.k2js.mavenbasicfw.util.SeleniumWaits;

public class AOSTestRunner {
	private WebDriver driver;

	private Homepage hp;

	String result = "fail";
	String errorMsg = "";
	String bn = null, rm = null, url = null;

	@AfterMethod
	public void closure() {
		// step - last step will be executed when execution is completed
		System.out.println(result);
		System.out.println(errorMsg);
		BrowserFactory.closeCurrentBrowserWindow();
	}

	@BeforeMethod
	public void preCondition() {
		try {
			// step1
			this.bn = CommonUtil.getPropertyvalue("config", "browserName");
			System.out.println(bn);

			// step2
			this.rm = CommonUtil.getPropertyvalue("config", "runmode");
			System.out.println(rm);

			// step3
			this.url = CommonUtil.getPropertyvalue("config", "url");
			System.out.println(url);

			// step4
			this.driver = BrowserFactory.getBrowser(bn, rm);
			System.out.println(this.driver);
			// step5
			BrowserFactory.launchApplication(url);
			// step 6
			Homepage hp = new Homepage(this.driver);
			System.out.println(hp);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verifyLoginPage_TestCase1() throws Throwable {

		result = "fail";
		errorMsg = "";

		try {

			String expectedTitle = CommonUtil.getPropertyvalue("homepage", "title");
			System.out.println(expectedTitle);

			String actualtitle = hp.getActualTitle();
			System.out.println(actualtitle);

			Assert.assertEquals(actualtitle.contains(expectedTitle), "titles are not same");

			result = "pass";

		} catch (Throwable t) {// a parent class of exceptions can handle any of its child exceptions

			CommonUtil.takeScreenshot(this.driver);
			errorMsg = t.getMessage();
			throw t;
		}
	}

	public void verifyerrormessageforwrongusername_Testcase2() throws Throwable {
		result = "fail";
		errorMsg = "";

		try {

			hp.clickuserIcon();

			CommonUtil.sleepTime(SeleniumWaits.LONG_WAIT);

			Loginpage lp = new Loginpage(this.driver);
			System.out.println(lp);
			// While calling take data from properties

			String un = CommonUtil.getPropertyvalue("loginpage", "username");
			lp.enterUserName(un);

			String pw = CommonUtil.getPropertyvalue("homepage", "password");
			lp.enterPassword(pw);

			lp.clickButton();
			CommonUtil.sleepTime(SeleniumWaits.SHORT_WAIT);

			String expErrMsg = CommonUtil.getPropertyvalue("loginpage", "errmsg");
			String actErrMsg = lp.getErrorMsg();

			Assert.assertEquals(actErrMsg, expErrMsg, "error messages are not same");

			result = "pass";

		} catch (Throwable t) {// parent exception can handle any of its child exception.
			CommonUtil.takeScreenshot(this.driver);
			errorMsg = t.getMessage();
			throw t;
		}
	}
	
	@AfterTest
	public void closebrowsers() {
		BrowserFactory.closeAllBrowsers();
		
	}
}
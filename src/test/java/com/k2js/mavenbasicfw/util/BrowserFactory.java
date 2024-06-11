package com.k2js.mavenbasicfw.util;

import java.io.FileNotFoundException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {

	private static WebDriver driver = null;

	private BrowserFactory() {

	}

	private static WebDriver getLocalBrowser(String browserName) {
		if (browserName.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			BrowserFactory.driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("Firefox")) {
			WebDriverManager.chromedriver().setup();
			BrowserFactory.driver = new FirefoxDriver();
		}
		return BrowserFactory.driver;
	}

	private static WebDriver getRemoteBrowser(String browserName) {
		if (browserName.equalsIgnoreCase("Chrome")) {
			ChromeOptions co = new ChromeOptions();
			try {
				String cloudURL = CommonUtil.getPropertyvalue("config", "RemoteCloudUrl");
				WebDriverManager.chromedriver().setup();

				BrowserFactory.driver = new RemoteWebDriver(new URL(cloudURL), co);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (browserName.equalsIgnoreCase("Firefox")) {
			ChromeOptions fo = new ChromeOptions();
			try {
				String cloudURL = CommonUtil.getPropertyvalue("config", "RemoteCloudUrl");
				WebDriverManager.chromedriver().setup();

				BrowserFactory.driver = new RemoteWebDriver(new URL(cloudURL), fo);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return BrowserFactory.driver;
	}
	 
	public static WebDriver getBrowser(String browserName, String runMode) {
		if(runMode.equalsIgnoreCase("local")) 
		return getLocalBrowser(browserName);
		else if(runMode.equalsIgnoreCase("remote"))
		return getRemoteBrowser(browserName);
		
		return null;
	}
	
	public static void closeCurrentBrowserWindow() {
		BrowserFactory.driver.close();
	}
	
	public static void closeAllBrowsers() {
		BrowserFactory.driver.quit();
	}
	
	public static void launchApplication(String appurl) {
		BrowserFactory.driver.get(appurl);
		
		BrowserFactory.driver.manage().window().maximize();
		
		BrowserFactory.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
		
	}
	
	public static void main(String[] args) {
		try {
			String bn = CommonUtil.getPropertyvalue("config", "browserName");
			String rm = CommonUtil.getPropertyvalue("config", "runmode");
			
			WebDriver driver = BrowserFactory.getBrowser(bn, rm);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

	

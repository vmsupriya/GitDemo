package com.k2js.mavenbasicfw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CommonUtil {

	public static String getPropertyvalue(String filename, String KeyName) throws FileNotFoundException {
		try (FileInputStream fis = new FileInputStream("testdata\\" + filename + ".properties")) {
			Properties pros = new Properties();
			pros.load(fis);
			return pros.getProperty(KeyName);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getCurrentdate() {
		LocalDateTime ldt = LocalDateTime.now();
		return ldt.toString().substring(0, ldt.toString().indexOf(".")).replace(":", "-");
	}
	//if we write WebElement then it is method overloading
	public static void takeScreenshot(WebDriver driver) {
		TakesScreenshot tss = (TakesScreenshot) driver;
		File src = tss.getScreenshotAs(OutputType.FILE);
		File dest = new File("screenshots\\" + getCurrentdate() + ".png");
		try {
			FileUtils.copyFile(src, dest);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}	
		
	public static void sleepTime(int giveTime) {
		try {
			Thread.sleep(giveTime);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		String url = "";

		try {
			url = getPropertyvalue("config", "url");
			System.out.println(url);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String date = getCurrentdate();
		System.out.println(date);

		WebDriverManager.chromedriver().setup();
		ChromeDriver cd = new ChromeDriver();
		cd.get(url);
		
		CommonUtil.sleepTime(SeleniumWaits.LONG_WAIT);
		
		CommonUtil.takeScreenshot(cd);
	}
}

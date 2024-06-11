package com.k2js.mavenbasicfw.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Homepage {

	private WebDriver driver;

	public Homepage(WebDriver driver) { // constructor
		super();
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	@FindBy(id="menuUser")
	private WebElement userIconElement;

	public String getActualTitle() {
		return this.driver.getTitle();
	}
	
	public void clickuserIcon() {
		
		this.userIconElement.click();
	}

}

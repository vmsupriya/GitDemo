package com.k2js.mavenbasicfw.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Registration {
	private WebDriver driver;
	

	

	public Registration(WebDriver driver) { //constructor
		super();
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

}

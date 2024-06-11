package com.k2js.mavenbasicfw.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Loginpage {
	
	private WebDriver driver;
	
	@FindBy(name= "username")
	private WebElement usernameTextBoxElement;
	
	@FindBy(name="password")
	private WebElement passwordTextBoxElement;
	
	@FindBy(id="sign_in_btnundefined")
	private WebElement signinbutton;
	
	@FindBy(id="signInResultMessage")
	private WebElement errormsg;
	
	@FindBy(xpath ="//a[text() = 'CREATE NEW ACCOUNT']")
	private WebElement link;
	
	
	public Loginpage(WebDriver driver) { //constructor
		super();
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	public void enterUserName(String uname) {
		this.usernameTextBoxElement.clear();
		this.usernameTextBoxElement.sendKeys(uname);
	}
	
	public void enterPassword(String pwd) {
		this.passwordTextBoxElement.clear();
		this.passwordTextBoxElement.sendKeys(pwd);
	}
	
	public void clickButton() {
		this.signinbutton.click();
	}
	
	public String getErrorMsg() {
		String str ="";
		str = this.errormsg.getText();
		System.out.println(str);
		return null;
	}

}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static constants.AppConstants.*;
import utils.ElementUtil;
import utils.StringUtils;

public class RegistrationPage {

	private WebDriver driver;
	private ElementUtil eleutil;

	By FirstName = By.id("input-firstname");
	By LastName = By.id("input-lastname");
	By Email = By.id("input-email");
	By Telephone = By.id("input-telephone");
	By Password = By.id("input-password");
	By Confirmpwd = By.id("input-confirm");

	By subscribe_Yes = By.xpath("(//label[@class='radio-inline']//input[@type='radio'])[1]");
	By subscribe_No = By.xpath("(//label[@class='radio-inline']//input[@type='radio'])[2]");

	By Agree = By.name("agree");
	By Continue = By.xpath("//*[@id='content']/form/div/div/input[2]");

	By title = By.tagName("h1");
	By logout = By.linkText("Logout");

	private final By register = By.linkText("Register");

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}

	public boolean userRegistration(String FirstName, String lastname, String phone, String password,
			String subscribe) {

		eleutil.waitForElementVisible(this.FirstName, DEFAULT_TIMEOUT).sendKeys(FirstName);		//this variable is used when the variable is declared at class level and parameterized method
		eleutil.doSendKeys(LastName, lastname);
//		eleutil.doSendKeys(Email, email);	Removing and passing through String Utils class
		eleutil.doSendKeys(Email, StringUtils.getEmail());
		eleutil.doSendKeys(Telephone, phone);
		eleutil.doSendKeys(Password, password);
		eleutil.doSendKeys(Confirmpwd, password);

		if (subscribe.equalsIgnoreCase("Yes")) {
			eleutil.doClick(subscribe_Yes);
		} else {
			eleutil.doClick(subscribe_No);
		}

//		 By default this action will happen
		eleutil.doClick(Agree);
		eleutil.doClick(Continue);

		/**
		 * We can use the wait in if condition waitforAllElementVisible - Contain list of web element , waitForElementVisible - search for only 1 element
		 * eleutil.waitForElementVisible(title, DEFAULT_TIMEOUT).getText(); 
		 * if(eleutil.doElementGetText(title).contains(SUCCESS_MESSAGE)) {
		 **/
		
		if (eleutil.waitForElementVisible(title, DEFAULT_TIMEOUT).getText().contains(SUCCESS_MESSAGE)) {
//		Once success message is received the account is logged in , to add new user we need to logout and return to registration page	
			eleutil.doClick(logout);
			eleutil.doClick(register);

			return true;
		}
		
		return false;
	}

}

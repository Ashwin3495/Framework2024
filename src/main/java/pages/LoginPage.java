package pages;

//  With this we can directly call the variable inside AppConstant without using reference of class name
import static constants.AppConstants.DEFAULT_PAGE_TITLE;
import static constants.AppConstants.DEFAULT_TIMEOUT;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import constants.AppConstants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import utils.ElementUtil;

@Epic(value = "Login page scenario, Add the Epic ID")
@Feature(value = "Login feature add the feature ID")
@Owner(value = "Ashwin")

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleutil;

	// TODO Auto-generated method stub

	// Keep locator and driver as private

	private final By email = By.id("input-email");
	private final By password1 = By.id("input-password");
	private final By forgotLinkPwd = By.linkText("Forgotten Password");
	private final By login = By.xpath("//input[@value='Login']");
	private final By register = By.linkText("Register");

	// Constructor to create driver

	public LoginPage(WebDriver driver) {

		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}

	public String getPageTitle() {
//		String title = driver.getTitle();

		String title = eleutil.waitFotTitleContains(DEFAULT_PAGE_TITLE, AppConstants.DEFAULT_TIMEOUT); // We can remove
																										// the
																										// Appconstant
																										// as we have
																										// imported the
																										// class
		System.out.println("login page " + title);
		return title;
	}

	@Step("Get the url")
	public String getPageUrl() {
//		String url = driver.getCurrentUrl();

		String url = eleutil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTION_URL, AppConstants.DEFAULT_TIMEOUT);
		System.out.println("login page url " + url);
		return url;
	}

	public boolean forgotPasswordLink() {
		return eleutil.isElementDisplayed(forgotLinkPwd);

//		return driver.findElement(forgotLinkPwd).isDisplayed();
	}

	@Step("Value of username : {0} and password :{1}")
	@Severity(SeverityLevel.CRITICAL)
	public AccountPage doLogin(String username, String password) {

		System.out.println("Username :" + username + " Password :" + password);
		eleutil.waitForElementVisible(email, AppConstants.DEFAULT_TIMEOUT).sendKeys(username);
		eleutil.doSendKeys(password1, password);
		eleutil.doClick(login);

		return new AccountPage(driver);

//		------- hard coded way of finding the element ----------- 
//		driver.findElement(email).sendKeys(username);
//		driver.findElement(password1).sendKeys(password);
//		driver.findElement(login).click();
//		String title = driver.getTitle();
//		String title = eleutil.waitFotTitleIs(AppConstants.HOME_PAGE_TITLE, AppConstants.DEFAULT_TIMEOUT);
//		System.out.println("Page Title : " + title);
//		return title;
	}

	public RegistrationPage navigatetoRegister() {
//		eleutil.waitForElementVisible(logout, DEFAULT_TIMEOUT);
//		eleutil.doClick(logout);

		eleutil.clickWhenReady(register, DEFAULT_TIMEOUT);
		return new RegistrationPage(driver);
	}
}

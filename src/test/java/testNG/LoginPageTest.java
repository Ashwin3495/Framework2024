package testNG;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import constants.AppConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

public class LoginPageTest extends BaseTest {

	// @Test class work in alphabetical order
	@Description("Login page title...")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "checking login page url....")
	@Step("Get the title of the page")
	public void loginPageTitleTest() {
		String title = lp.getPageTitle();
		Assert.assertEquals(title, AppConstants.DEFAULT_PAGE_TITLE);

	}

	@Test
	public void loginPageURLTest() {

		String title = lp.getPageUrl();
		Assert.assertTrue(title.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));

	}

	@Test
	public void forgotLinkeTest() {
		boolean forgotLink = lp.forgotPasswordLink();
		Assert.assertTrue(forgotLink);

	}

	@Test(priority = Short.MAX_VALUE)
	@Step("Value of username ")
	public void doLogin() throws InterruptedException {
//		String title = lp.doLogin("ashwin5@gmail.com", "Ashwin123#");
		acp = lp.doLogin(pf.getProperty("username"), pf.getProperty("password"));

		Assert.assertEquals(acp.getTitle(), AppConstants.HOME_PAGE_TITLE);
	}
}

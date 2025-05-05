package testNG;

import static constants.AppConstants.HOME_PAGE_FRACTION_URL;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import constants.AppConstants;
import io.qameta.allure.Step;

public class AccountPageTest extends BaseTest {
	@Step("Account Page setup")
	@BeforeClass
	public void accPagesetup() throws InterruptedException {
		acp = lp.doLogin(pf.getProperty("username"), pf.getProperty("password")); // this will return the next landing
																					// page and it is stored in
																					// reference variable of the page
	}

	// with out login we will not be able to run the below test
	@Step("Account Page Title test")
	@Test
	public void accountPageTitleTest() {
		String title = acp.getTitle();
		Assert.assertEquals(title, AppConstants.HOME_PAGE_TITLE);

	}

	@Step("Account Page Url")
	@Test
	public void accountPageUrlTest() {
		String title = acp.getUrl();
		Assert.assertTrue(title.contains(HOME_PAGE_FRACTION_URL));

	}

	@Step("Account Page Header")
	@Test
	public void accountPageHeaderTest() {
		List<String> actualHeaders = acp.getHeaders();
		Assert.assertEquals(actualHeaders, AppConstants.expectedheaders);
	}

}

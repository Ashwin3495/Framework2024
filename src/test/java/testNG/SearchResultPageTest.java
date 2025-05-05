package testNG;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import io.qameta.allure.Description;

public class SearchResultPageTest extends BaseTest{

	@BeforeClass
	public void searchsetup() throws InterruptedException {
		acp = lp.doLogin(pf.getProperty("username"), pf.getProperty("password"));
	}
	
	@Description("Search Count Test")
	@Test
	public void getsearchCountTest() {
		srp=acp.doProductSearch("macbook");			// Invalid text which is not present in product catalog
		int actual_search = srp.getsearchresult();
		Assert.assertEquals(actual_search, 3);			// Invalid text will return 0 instead of Exception
		
	}
	
	
	
}

package testNG;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static constants.AppConstants.*;

import base.BaseTest;
import utils.ExcelUtil;

public class RegistrationPageTest extends BaseTest {

	@BeforeClass
	public void registersetup() {
		registerpage = lp.navigatetoRegister();
	}

	@DataProvider
	// Removing the email and passing it through the StringUtils
	public Object[][] getregisterUser() {
		return new Object[][] { { "jslw", "jskia", "6251284521", "Public7845", "yes" },
				{ "urie", "dsaw", "6551284521", "Public7875", "yes" },
				{ "jslsw", "jskisa", "6251284521", "Public7845", "yes" }, };
	}
	
	
	@DataProvider
	public Object[][] getRegisterData() {
		Object regData[][]=ExcelUtil.getTestData(REGISTER_SHEET_NAME);
		return regData;
	}

	@Test(dataProvider = "getRegisterData")
	public void userRegistration(String firstname, String lastname, String phoneNo, String password, String subcribe) {
		Assert.assertTrue(registerpage.userRegistration(firstname, lastname, phoneNo, password, subcribe));
	}

}

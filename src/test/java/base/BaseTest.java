package base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;

import factory.DriverFactory;
import io.qameta.allure.Description;
import pages.AccountPage;
import pages.LoginPage;
import pages.ProductPage;
import pages.RegistrationPage;
import pages.SearchResultPage;
import utils.LogUtil;

@Listeners(ChainTestListener.class)
public class BaseTest {

	WebDriver driver;

	DriverFactory df;
//	LoginPage lp;	//Cannot be access since access modifier is default - accessed only within the same package

	// protected allows accessing the object reference outside the package
	protected LoginPage lp;
	protected Properties pf;
	protected AccountPage acp;
	protected SearchResultPage srp;
	protected ProductPage productpage;
	protected RegistrationPage registerpage;

	@Description("Opening of Browser")
	@Parameters({ "browser_test" }) // value received from testNG will be passed as parameter in setup method to
									// browsername
	@BeforeTest	
//	@BeforeMethod
	public void setup(String browsername) throws Exception { // we cannot pass browser as it is @Optional("chrome")
																					// string

		df = new DriverFactory(); // object creation
		pf = df.initProp(); // Initialize property file to get the browser name

		if (browsername != null) {
			pf.setProperty("browser", browsername); // Set browser (make sure key matches exactly!) name given in config
													// file must be mentioned
		}

		driver = df.initDriver(pf);
		lp = new LoginPage(driver);
//		acp= new AccountPage(driver);
//		srp= new SearchResultPage(driver);
//		productpage= new ProductPage(driver);
//		registerpage= new RegistrationPage(driver);
		
		// -------------------------------------------------------------------------------------------//
//		driver=df.initDriver("chrome");	-- hardcoded value been set now with properties file it will fetch

	}

	@BeforeMethod
	public void beforemethod(ITestContext result) {
		LogUtil.info("============Starting test method ============" + result.getName());
	}

	@AfterMethod // will be running after each @test method
	public void attachScreenshot(ITestResult result) {
		if (!result.isSuccess()) {// only for failure test cases -- true
//			log.info("---screenshot is taken---");
			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
		}

		// ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png"); //
		// get the screenshot for every test case irrespective of pass or fail

		LogUtil.info("============Ending method ============" + result.getMethod().getMethodName());
//		if (driver != null) {
//			driver.quit();
//		}
	}

//	@Description("Quiting the browser...")
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}

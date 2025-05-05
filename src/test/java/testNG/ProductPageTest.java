package testNG;

import static constants.AppConstants.PRODUCTINFO_SHEET_NAME;
import static constants.AppConstants.PRODUCT_SHEET_NAME;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseTest;
import utils.CSVUtil;
import utils.ExcelUtil;

public class ProductPageTest extends BaseTest {

	@BeforeClass
	public void productsetup() {
		acp = lp.doLogin(pf.getProperty("username"), pf.getProperty("password")); // On the login it will redirect to
																					// Account Page
	}

//	@BeforeMethod			// Running the common method
//	public void producPagesetupTest() {
//		srp = acp.doProductSearch("macbook"); // Using the account page method searching the product it will provide the result of the search
//		productpage = srp.selectProduct("MacBook"); // On selecting the product it will redirect to Product page
//	}

	@DataProvider
	public Object[][] getproductHeaderData() {
		return new Object[][] { { "macbook", "MacBook" }, { "imac", "iMac" }, };
	}

	@Test(dataProvider = "getproductHeaderData",enabled=false) // must give method name and store in ""
	public void productHeaderTest(String searchKey, String selectProduct) {
		/*
		 * srp = acp.doProductSearch("macbook"); // Common method in both the Test
		 * productpage = srp.selectProduct("MacBook"); String actualheader =
		 * productpage.getProductHeader(); Assert.assertEquals(actualheader, "MacBook");
		 */

		srp = acp.doProductSearch(searchKey); // Common method in both the Test
		productpage = srp.selectProduct(selectProduct);
		String actualheader = productpage.getProductHeader();
		Assert.assertEquals(actualheader, selectProduct);

	}

	@DataProvider
	public Object[][] getproductImageCount() {
		return new Object[][] { 
			{ "macbook", "MacBook", 5 }, 
			{ "imac", "iMac", 3 }, 
			};
	}
	
	//Excel data
	@DataProvider
	public Object[][] getProductImage(){
		Object[][] productImage=ExcelUtil.getTestData(PRODUCT_SHEET_NAME);
		return productImage;
	}
	
	// CSV data
	@DataProvider
	public Object[][] getProductImageCSV(){
		Object[][] productImagecsv=CSVUtil.csvData("Open");		// Directly provide the csv file name
		return productImagecsv;
	}

	@Test(dataProvider="getProductImageCSV")
//	public void productImageCountTest(String searchKey, String selectProduct, int ImageCount)  We need to convert the int to String as we are maintaining everything in String
	public void productImageCountTest(String searchKey, String selectProduct, String ImageCount) {
		srp = acp.doProductSearch(searchKey); 		//	Common method in both the Test
		productpage = srp.selectProduct(selectProduct); 
		int actualimage = productpage.getProductImageCount();
		Assert.assertEquals(String.valueOf(actualimage),ImageCount);
		
//		We can convert the actual image to string by using String.valueOf(actualimage)
	}

	@Test(enabled=false)
	public void productInfoTest() {
		srp = acp.doProductSearch("macbook"); // Common method in both the Test
		productpage = srp.selectProduct("MacBook");
		Map<String, String> actualProductDetailsMap = productpage.getProductDetailsMap();
		SoftAssert softassert = new SoftAssert();

		Assert.assertEquals(actualProductDetailsMap.get("Brand"), "Apple"); // get we are sending the key expected =
																			// value for assertion purpose
		Assert.assertEquals(actualProductDetailsMap.get("extaxprice"), "$500.00");

		Assert.assertEquals(actualProductDetailsMap.get("Availability"), "In Stock");
		Assert.assertEquals(actualProductDetailsMap.get("productimages"), "5");

		softassert.assertAll();
	}
	
	
	@DataProvider
	public Object[][] getProductInfo(){
		Object[][] productImage=ExcelUtil.getTestData(PRODUCTINFO_SHEET_NAME);
		return productImage;
	}
	
	@Test(dataProvider="getProductInfo")
	public void productInfoTest(String searchKey, String selectProduct,String Brand, String extraprice, String availability) {
		srp = acp.doProductSearch(searchKey); 		//	Common method in both the Test
		productpage = srp.selectProduct(selectProduct); 
		Map<String, String> actualProductDetailsMap = productpage.getProductDetailsMap();
		String actualbrand = actualProductDetailsMap.get("Brand");
		String actualextraprice = actualProductDetailsMap.get("extaxprice");
		String actualavailability = actualProductDetailsMap.get("Availability");
		Assert.assertEquals(actualbrand, Brand);
		Assert.assertEquals(actualextraprice, extraprice);
		Assert.assertEquals(actualavailability, availability);
	}
}

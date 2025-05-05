package pages;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import constants.AppConstants;
import utils.ElementUtil;

public class ProductPage {

	private WebDriver driver;
	private ElementUtil eleutil;

	By productImage = By.cssSelector("div > ul img");
	By productHeader = By.cssSelector("div > h1");

//	By productmetaDatacss = By.cssSelector("div#content > div > div > ul.list-unstyled>li");
	By productmetaData = By.xpath("(//div[@id='content']//div/ul[@class='list-unstyled'])[1]/li");
	By productPriceData = By.xpath("(//div[@id='content']//div/ul[@class='list-unstyled'])[2]/li");

//	Map<String, String> productDetails = new HashMap<>();
//	Map<String, String> productDetails = new LinkedHashMap<>();
	Map<String, String> productDetails = new TreeMap<>();
	
	public ProductPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}

	public Map<String, String> getProductDetailsMap() {
		getProductMetaData();
		getProductPrice();
		productDetails.put("productimages", String.valueOf(getProductImageCount()));		// return type int converted to String
		productDetails.put("productheader", getProductHeader());			// returning the header in string but no key we need to give the key

		System.out.println("Product Details :" + productDetails);
		return productDetails;
	}

//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: Out Of Stock
	private void getProductMetaData() {

		/**
		 * getElementTextList(productmetaData) returns a List<String> — the text values
		 * of the elements. Then, the loop iterates directly over the text content (like
		 * "Brand: Apple"). This is a clean and direct approach when you only need the
		 * text of the elements.
		 */

//		eleutil.waitForAllElementsVisible(productmetaData, AppConstants.DEFAULT_TIMEOUT);
//		List<String> productData=eleutil.getElementTextList(productmetaData);
//	
//		for(String e : productData) {
//		String meta[]=e.split(":");
//		String productKey=meta[0].trim();
//		String productValue=meta[1].trim();
//		
//		productDetails.put(productKey, productValue);
//		}

		/**
		 * Gets a List<WebElement> — the actual elements from the DOM. Then, for each
		 * WebElement, it calls getText() individually. This gives more flexibility
		 * (e.g., you can access attributes, tag names, do conditional logic).
		 */

		List<WebElement> metadata = eleutil.waitForAllElementsVisible(productmetaData, AppConstants.DEFAULT_TIMEOUT);
		for (WebElement e : metadata) {
			String metaa = e.getText();
			String meta[] = metaa.split(":");
			String productKey = meta[0].trim();
			String productValue = meta[1].trim();

			productDetails.put(productKey, productValue);
		}

	}

	private void getProductPrice() {

//		List<String> priceList = eleutil.getElementTextList(productPriceData);
//		String productprice = priceList.get(0); // $2,000.00
//		String exTaxPrice = priceList.get(1).split(":")[1].trim(); // Ex Tax: $2,000.00
//		productDetails.put("productprice", productprice);
//		productDetails.put("extaxprice", exTaxPrice);

		List<WebElement> metaprice=eleutil.waitForAllElementsVisible(productPriceData, AppConstants.DEFAULT_TIMEOUT);
		String productPrice=metaprice.get(0).getText();		// $2,000.00
		String productextaxPrice=metaprice.get(1).getText().split(":")[1].trim();		// Ex Tax: $2,000.00
		productDetails.put("productprice", productPrice);
		productDetails.put("extaxprice",productextaxPrice);
		
	}

	public int getProductImageCount() {

		eleutil.waitForElementVisible(productImage, AppConstants.DEFAULT_TIMEOUT);
		int imagecount = eleutil.getElements(productImage).size();
		return imagecount;
	}

	public String getProductHeader() {

		String productheader = eleutil.waitForElementVisible(productHeader, AppConstants.MEDIUM_TIMEOUT).getText();
		return productheader;
	}

}

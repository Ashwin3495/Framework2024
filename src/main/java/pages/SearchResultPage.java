package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import constants.AppConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import utils.ElementUtil;

public class SearchResultPage {

	// Always maintain WebDriver and Element util as private
	private WebDriver driver;
	private ElementUtil eleutil;
	
	private final By resultsProduct=By.cssSelector("div > .product-thumb");
	
	public SearchResultPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		eleutil=new ElementUtil(driver);
	
	}
	
	@Description("Result Count of Search")
	public int getsearchresult() {
		
		// careless coding 
//		int result = eleutil.getElementsCount(resultsProduct);
//		return result;
		
		
// 		thoughtful coding  ---  after entering and click of search we need to wait for element to load
		int searchCount = 
				eleutil.waitForAllElementsVisible(resultsProduct, AppConstants.MEDIUM_TIMEOUT).size();
		System.out.println("total number of search products: "+ searchCount);
		return searchCount;
	}
	
	@Step("Text being searched : {0}")
	public ProductPage selectProduct(String text)  {
		System.out.println("Entered text :"+text);
		eleutil.waitForAllElementsVisible(resultsProduct,AppConstants.DEFAULT_TIMEOUT);
		 eleutil.doClick(By.linkText(text));	//on the click it will redirect to next page
		 return new ProductPage(driver);
	}

}

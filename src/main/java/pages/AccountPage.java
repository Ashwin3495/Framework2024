package pages;

import static constants.AppConstants.DEFAULT_TIMEOUT;
import static constants.AppConstants.HOME_PAGE_FRACTION_URL;
import static constants.AppConstants.HOME_PAGE_TITLE;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import utils.ElementUtil;

public class AccountPage {

	// Always maintain WebDriver and Element util as private
	private WebDriver driver;
	private ElementUtil eleutil;

	private final By headers = By.cssSelector("div#content > h2");
	private final By search = By.cssSelector("div#search > input[name='search']");
	private final By searchIcon = By.cssSelector("#search span button.btn.btn-default.btn-lg");

	public AccountPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}

	public String getTitle() {
		String title = eleutil.waitFotTitleIs(HOME_PAGE_TITLE, DEFAULT_TIMEOUT);
		System.out.println("login page " + title);
		return title;

	}

	public String getUrl() {
		String url = eleutil.waitForURLContains(HOME_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
		System.out.println("URL page :" + url);
		return url;
	}

	public List<String> getHeaders() {
		List<WebElement> headerList = eleutil.getElements(headers); // it contains all the locator
		List<String> headers = new ArrayList<>(); // it will store the element

		for (WebElement e : headerList) {
			headers.add(e.getText());
		}
		return headers;
	}

	@Step("Searching for text : {0}")
	@Severity(SeverityLevel.CRITICAL)
	public SearchResultPage doProductSearch(String searchtext) {

		System.out.println("Search for :" + searchtext);
		eleutil.waitForElementVisible(search, DEFAULT_TIMEOUT);
//		driver.findElement(search).clear();		// Clear the search box before every search
		eleutil.doSendKeys(search, searchtext);
		eleutil.doClick(searchIcon);

		return new SearchResultPage(driver);
	}

}

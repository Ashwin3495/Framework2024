package factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import exceptions.FrameworkException;
import io.qameta.allure.Step;
import utils.LogUtil;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsmanager;

	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();

	public static String highlight;
	public static Logger log = LogManager.getLogger(DriverFactory.class);
////	public WebDriver initDriver(String browsername)  // hardcoded value

	@Step("Initializing the browser : {prop}")
	public WebDriver initDriver(Properties prop) throws Exception {
		LogUtil.info(prop.toString());
		String browsername = prop.getProperty("browser");
		log.info("browser name :" + browsername);

		optionsmanager = new OptionsManager(prop); // Initialize the option manager
		highlight = prop.getProperty("highlight");

		switch (browsername.toLowerCase().trim()) {
		case "chrome":

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// To run in remote/selenium grid/aws machine
				initRemoteDriver("chrome");
			} else {
//			driver = new ChromeDriver(optionsmanager.getChromeOptions());
				tldriver.set(new ChromeDriver(optionsmanager.getChromeOptions()));
			}
			break;

		case "edge":
			
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// To run in remote/selenium grid/aws machine
				initRemoteDriver("edge");
			} else {
//				driver = new EdgeDriver(optionsmanager.getEdgeOptions());
			tldriver.set(new EdgeDriver(optionsmanager.getEdgeOptions()));
			}
			break;
			
			

		case "firefox":
//			driver = new FirefoxDriver(optionsmanager.getFirefoxOptions());
			tldriver.set(new FirefoxDriver(optionsmanager.getFirefoxOptions()));
			break;

		default:
			System.out.println("Plz provide correct driver name " + browsername);
			log.error("===========Plz provide correct driver name==============" + browsername);

			throw new FrameworkException("=========== INVALID BROWSER NAME ===========");
		}

		System.out.println("Driver name " + prop.getProperty("browser"));

		getDriver().get(prop.getProperty("url")); // changing the driver with getDriver() method which returns the
													// driver in BaseTest class
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
//		return driver;
		return getDriver(); // Once it retuns then thread local will work

	}

	private void initRemoteDriver(String browserName) {
		// TODO Auto-generated method stub

		switch (browserName) {
		case "chrome":
			try {
				tldriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsmanager.getChromeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			break;

		case "firefox":
			try {
				tldriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsmanager.getFirefoxOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			break;

		case "edge":
			try {
				tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsmanager.getEdgeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			break;

		default:
			System.out.println("Entered browser not supported");
			break;
		}
	}

	public static WebDriver getDriver() {
		return tldriver.get();
	}

	public Properties initProp() {

		String envname = System.getProperty("env");
		FileInputStream fis = null;
		prop = new Properties();
		try {

			if (envname == null) {
				System.out.println("Running the default as environment variable is null");
				log.warn("============Running the default as environment variable is null======================");
				fis = new FileInputStream("./src/test/resources/config/config.properties");
			} else {
				System.out.println("Running the test on " + envname);
				switch (envname.toLowerCase().trim()) {
				case "qa":
					fis = new FileInputStream("./src/test/resources/config/config_qa.properties");
					break;
				case "uat":
					fis = new FileInputStream("./src/test/resources/config/config_uat.properties");
					break;
				case "dev":
					fis = new FileInputStream("./src/test/resources/config/config_uat.properties");
					break;
				case "stage":
					fis = new FileInputStream("./src/test/resources/config/config_uat.properties");
					break;
				default:
					log.error("================ INVALID BROWSER NAME ====================");
					throw new FrameworkException("================ INVALID BROWSER NAME ====================");
				}
			}

			prop.load(fis); // will throw null pointer exception

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}
}

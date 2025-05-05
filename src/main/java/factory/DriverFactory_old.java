package factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import exceptions.BrowserException;

public class DriverFactory_old {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsmanager;
	
	public static String highlight;

	
//////	public WebDriver initDriver(String browsername)  // hardcoded value

	public WebDriver initDriver(Properties prop) throws Exception {
		
		String browsername = prop.getProperty("browser");
		optionsmanager=new OptionsManager(prop);					// Initialize the option manager
		highlight=prop.getProperty("highlight");
		
		
		switch (browsername) {
		case "chrome":
			driver = new ChromeDriver(optionsmanager.getChromeOptions());
			break;

		case "edge":
			driver = new EdgeDriver(optionsmanager.getEdgeOptions());
			break;

		case "firefox":
			driver = new FirefoxDriver(optionsmanager.getFirefoxOptions());
			break;

		default:
			System.out.println("Plz provide correct driver name " + prop.getProperty("browser"));
			throw new BrowserException("=========== INVALID BROWSER NAME ===========");
		}

		System.out.println("Driver name " + prop.getProperty("browser"));
		
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;
	}

	public Properties initProp() {
		prop = new Properties();
		try {

			FileInputStream fis = new FileInputStream("./src/test/resources/config/config.properties");
			prop.load(fis); // will throw null pointer exception

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

}




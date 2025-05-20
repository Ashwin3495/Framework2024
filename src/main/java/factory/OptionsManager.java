package factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	Properties prop;

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {

		ChromeOptions co = new ChromeOptions();
		if ( Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("Headless");
//			log.info("==== Running in headless mode===");
			co.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("Incognito");
//			log.info("==== Running in incognito mode===");
			co.addArguments("--incognito");
		}
		// running in remote mode
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setCapability("browserName", "chrome");
		}
		return co;
	}

	public FirefoxOptions getFirefoxOptions() {

		FirefoxOptions fo = new FirefoxOptions();
		if ( Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("Headless");
			fo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("Incognito");
			fo.addArguments("--incognito");
		}
		// running in remote mode
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			fo.setCapability("browserName", "firefox");
		}
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() {

		EdgeOptions eo = new EdgeOptions();
		if ( Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("Headless");
			eo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("Incognito");
			eo.addArguments("--incognito");
		}
		// running in remote mode
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			eo.setCapability("browserName", "edge");
		}
		return eo;
	}
}

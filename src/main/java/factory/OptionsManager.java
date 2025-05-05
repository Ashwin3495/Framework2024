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
			co.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("Incognito");
			co.addArguments("--incognito");
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
		return eo;
	}
}

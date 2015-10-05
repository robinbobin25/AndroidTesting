package driver;

import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import readers.PropertiesReader;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.remote.CapabilityType.VERSION;

public class DriverFactory {

    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);
    private static WebDriver driver;
    private static PropertiesReader propertiesReader = new PropertiesReader(); //UtilsConstants.BROWSERS_CONFIGURATION);
    private final String BROWSER_TYPE = propertiesReader.getPropertyValue("browser.type");
    private final String CHROME_BROWSER_VERSION = propertiesReader.getPropertyValue("chrome.versions");
    private final String FIREFOX_BROWSER_VERSION = propertiesReader.getPropertyValue("firefox.versions");
    private final String HUB_URL = propertiesReader.getPropertyValue("hub.url");


    public WebDriver getDriverInstance(String androidVersion) {
        if (BROWSER_TYPE.equals("firefox")) {
            driver = createFirefoxDriver();
            optimizeDriver(driver);
            return driver;
        } else if (BROWSER_TYPE.equals("chrome")) {
            driver = createChromeDriver();
            optimizeDriver(driver);
            return driver;
        } else if (BROWSER_TYPE.equals("android")) {
            driver = createAndroidDriver(androidVersion);
            return driver;
        } else {
            throw new IllegalArgumentException("This browser is undefined!");
        }
    }

    protected WebDriver createChromeDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        System.setProperty("webdriver.chrome.driver", ""); //UtilsConstants.CHROME_EXE);
        capabilities.setVersion(CHROME_BROWSER_VERSION);
        return new ChromeDriver(capabilities);
    }

    protected WebDriver createFirefoxDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setVersion(FIREFOX_BROWSER_VERSION);
        return new FirefoxDriver(capabilities);
    }

    protected WebDriver createAndroidDriver(String androidVersion) {
        SelendroidDriver driver = null;
        DesiredCapabilities capabilities = SelendroidCapabilities.android();
        capabilities.setBrowserName("android");
        capabilities.setCapability(VERSION, androidVersion);
        try {
            driver = new SelendroidDriver(new URL(HUB_URL), capabilities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

    private void optimizeDriver(WebDriver driver) {
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

}

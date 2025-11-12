package general;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static config.ConfigProperties.*;

public class WebDriverFactory {
    private static ThreadLocal<WebDriver> driverThreadLocal = ThreadLocal.withInitial(() -> null);
    private static String device = Device; // Change to windows or mac accordingly
    private static Boolean headless = Boolean.valueOf(isHeadless);

    public static final String USERNAME = "alvinakamran1";
    public static final String AUTOMATE_KEY = "J26gKTS1gDw9WVEEcsYx";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public static WebDriver getInstance() throws MalformedURLException {
        if (driverThreadLocal.get() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions op = new ChromeOptions();

            if (headless)
                op.addArguments("--headless");

            switch (Browser) {
                case "Chrome":
                    op.addArguments("--start-maximized");
                    op.addArguments("--disable-save-password-bubble");
                    op.addArguments("--disable-password-manager-reauthentication");
                    op.addArguments("--disable-password-manager-automatic-sign-in");
                    op.addArguments("--disable-save-password-bubble");
                    op.addArguments("--disable-password-manager-reauthentication");
                    op.addArguments("--disable-password-manager-automatic-sign-in");
                    op.addArguments("--no-default-browser-check");
                    op.addArguments("--disable-notifications");
                    op.addArguments("--disable-infobars");
                    op.addArguments("--start-maximized");
                    op.addArguments("--disable-extensions");
                    op.addArguments("--disable-component-update");
                    op.addArguments("--disable-sync"); // explicit
                    op.addArguments("--disable-background-networking");
                    op.addArguments("--disable-default-apps");
                    op.addArguments("--disable-features=PasswordLeakDetection,AutofillServerCommunication,PasswordManagerRedirection");

//                 op.addArguments("--headless");
                    driverThreadLocal.set(new ChromeDriver(op));
                    break;

                case "IE":
                    driverThreadLocal.set(new InternetExplorerDriver());
                    break;

                case "Firefox":
                    op.addArguments("--start-maximized");
                    driverThreadLocal.set(new FirefoxDriver());
                    break;

                case "Remote":
                    DesiredCapabilities caps = DesiredCapabilities.chrome();
                    caps.setCapability("browser", "Chrome");
                    caps.setCapability("browser_version", "78.0 beta");
                    caps.setCapability("os", "Windows");
                    caps.setCapability("os_version", "10");
                    caps.setCapability("resolution", "1024x768");
                    driverThreadLocal.set(new RemoteWebDriver(new URL(URL), caps));
            }

            driverThreadLocal.get().get(baseUrl);
            driverThreadLocal.get().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        }

        return driverThreadLocal.get();
    }

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() != null) {
            return driverThreadLocal.get();
        } else {
            throw new IllegalStateException("Driver has not been initialized");
        }
    }

    public static void finishDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
package com.enterprise.framework.factory;

import com.enterprise.framework.config.ConfigManager;
import com.enterprise.framework.enums.BrowserType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * DriverFactory - Creates WebDriver instances using the Factory Pattern.
 *
 * INTERN NOTE: The Factory Pattern separates object creation from usage.
 * Tests never say "new ChromeDriver()" directly - they ask the factory.
 * Switching from Chrome to Firefox requires ZERO test code changes.
 *
 * Usage: WebDriver driver = DriverFactory.createDriver();
 * Browser is read from: -Dbrowser=firefox  OR  config browser.default=chrome
 */
public final class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    private DriverFactory() {}

    /**
     * Creates, configures, and returns a WebDriver instance.
     * Browser and headless mode are resolved from system props / config.
     */
    public static WebDriver createDriver() {
        ConfigManager config   = ConfigManager.getInstance();
        String browserValue    = System.getProperty("browser", config.getDefaultBrowser());
        boolean headless       = Boolean.parseBoolean(
            System.getProperty("headless", config.get("browser.headless")));
        BrowserType browser    = BrowserType.fromString(browserValue);

        log.info("Creating [{}] driver | headless={}", browser, headless);

        WebDriver driver = switch (browser) {
            case CHROME  -> createChromeDriver(headless);
            case FIREFOX -> createFirefoxDriver(headless);
            case EDGE    -> createEdgeDriver(headless);
            case SAFARI  -> createSafariDriver();
        };

        configureTimeouts(driver, config);
        driver.manage().window().maximize();
        log.info("[{}] driver ready", driver.getClass().getSimpleName());
        return driver;
    }

    // ---- Browser builders ------------------------------------------------

    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments(
            "--disable-infobars",
            "--disable-extensions",
            "--disable-popup-blocking",
            "--disable-notifications",
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--window-size=1920,1080",
            "--remote-allow-origins=*"
        );
        if (headless) opts.addArguments("--headless=new");
        return new ChromeDriver(opts);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions opts = new FirefoxOptions();
        if (headless) opts.addArguments("-headless");
        return new FirefoxDriver(opts);
    }

    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions opts = new EdgeOptions();
        if (headless) opts.addArguments("--headless=new");
        return new EdgeDriver(opts);
    }

    private static WebDriver createSafariDriver() {
        try {
            Class<?> cls = Class.forName("org.openqa.selenium.safari.SafariDriver");
            return (WebDriver) cls.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(
                "Safari driver failed. Enable 'Allow Remote Automation' in Safari Develop menu.", e);
        }
    }

    // ---- Timeout config --------------------------------------------------

    private static void configureTimeouts(WebDriver driver, ConfigManager config) {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getInt("browser.pageload.timeout")));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(config.getInt("browser.script.timeout")));
        // Implicit wait = 0 (industry best practice - always use Explicit waits)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }
}

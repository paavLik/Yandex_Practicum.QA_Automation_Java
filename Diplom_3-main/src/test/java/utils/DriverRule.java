package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.time.Duration;

public class DriverRule extends ExternalResource {
    private WebDriver driver;

    @Override
    protected void before() throws Throwable {
        initDriver();
    }

    @Override
    protected void after() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void initDriver() {
        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = "chrome"; // значение по умолчанию
        }
        System.out.println("Initializing browser: " + browser);  // Debug output

        if ("firefox".equals(browser)) {
            initFirefox();
        } else if ("chrome".equals(browser)) {
            initChrome();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    private void initFirefox() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize(); // Установка окна в максимальный размер для Firefox
    }

    private void initChrome() {
        // Установите путь к ChromeDriver из src/test/resources
        String chromeDriverPath = new File("src/test/resources/chromedriver").getAbsolutePath();
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        // Проверьте и выведите путь к ChromeDriver для отладки
        System.out.println("ChromeDriver path: " + chromeDriverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); // Опция для Chrome 114 и выше
        driver = new ChromeDriver(options);
        driver.manage().window().maximize(); // Установка окна в максимальный размер для Chrome
    }

    public WebDriver getDriver() {
        return driver;
    }
}

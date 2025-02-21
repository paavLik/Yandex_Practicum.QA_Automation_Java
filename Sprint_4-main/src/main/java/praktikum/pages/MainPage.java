package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

public class MainPage {

    private final WebDriver driver;

    // кнопка "Заказать"  вверху страницы
    private final By orderUp = By.xpath(".//button[@class = 'Button_Button__ra12g']");
    // кнопка "Статус заказа"
    private final By orderStatus = By.xpath(".//button[contains(text(), 'Статус заказа')]");
    // поле для ввода номера заказа
    private final By orderStatusField = By.xpath(".//input[@ placeholder = 'Введите номер заказа']");
    // кнопка "GO" для начала поиска заказа
    private final By goButton = By.cssSelector("[class*=Header_Button__]");
    // кнопка "да все привыкли"
    private final By approval = By.xpath("//button[contains(text(), 'да все привыкли')]");
    // кнопка "Заказать" внизу страницы
    private final By orderDown = By.xpath(".//div[@class = 'Home_FinishButton__1_cWm']/button[contains(text(), 'Заказать')]");
    // Логотип "Яндекс"
    private final By logoYandex = By.xpath(".//img[@alt = 'Yandex']");
    // Логотип Самокат
    private final By logoScooter = By.className("Header_LogoScooter__3lsAR");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод нажатия на логотип "Самокат"
    public MainPage clickLogoScooter() {
        driver.findElement(logoScooter).click();
        return this;
    }

    // Метод нажатия на кнопку "Заказать" вверху страницы
    public MainPage clickOrderUp() {
        driver.findElement(orderUp).click();
        return this;
    }

    // метод нажатия кнопки "Статус заказа"
    public MainPage clickOrderStatus() {
        driver.findElement(orderStatus).click();
        return this;
    }

    // метод для ввода номера заказа
    public MainPage enterOrderNumber(String orderNumber) {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(orderStatusField));
        driver.findElement(orderStatusField).sendKeys(orderNumber);
        return this;
    }

    // метод нажатия на кнопку "GO"
    public MainPage clickGo() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(orderStatusField));
        driver.findElement(goButton).click();
        return this;
    }

    // Метод , который скроллит до кнопки "Заказать" внизу
    public void scrollToOrderDown() {
        WebElement orderDownElement = driver.findElement(orderDown);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", orderDownElement);
    }

    // метод нажатия на кнопку "да все привыкли"
    public MainPage clickApproval() {
        driver.findElement(approval).click();
        return this;
    }

    // Метод нажатия на кнопку "Заказать" внизу страницы
    public MainPage clickOrderDown() {
        scrollToOrderDown();
        driver.findElement(orderDown).click();
        return this;
    }

    // Метод нажатия на логотип "Яндекс"
    public MainPage clickLogoYandex() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(logoYandex));
        driver.findElement(logoYandex).click();
        return this;
    }

    // метод открытия главной страницы
    public MainPage open() {
        driver.get(EnvConfig.BASE_URL);
        return this;
    }
}

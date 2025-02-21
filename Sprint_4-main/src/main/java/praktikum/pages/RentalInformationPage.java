package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

public class RentalInformationPage {

    private final WebDriver driver;

    // поле для ввода даты доставки заказа
    private final By deliveryDateField = By.xpath(".//input[@placeholder ='* Когда привезти самокат']");
    // выпадающий список для выбора срока аренды
    private final By rentalPeriod = By.className("Dropdown-root");
    // локатор даты срока аренды
    private final By day19 = By.xpath("//div[contains(text(), '19')]");
    // поле для ввода комментария
    private final By commentField = By.xpath(".//input[@placeholder ='Комментарий для курьера']");

    // кнопка "Заказать"
    private final By orderButton = By.xpath(".//button[@class = 'Button_Button__ra12g Button_Middle__1CSJM']");

    public RentalInformationPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод для ввода даты доставки
    public RentalInformationPage setDeliveryDateField(String date) {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(deliveryDateField));

        driver.findElement(deliveryDateField).clear();
        driver.findElement(deliveryDateField).sendKeys(date);
        driver.findElement(day19).click();
        return this;
    }

    // Метод для нажатия на выпадающий список срока аренды
    public RentalInformationPage clickRentalPeriodDropdown() {
        driver.findElement(rentalPeriod).click();
        return this;
    }

    // Метод для выбора цвета самоката
    public RentalInformationPage selectColor(String color) {
        By colorScooter = By.id(color);
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(colorScooter));
        driver.findElement(colorScooter).click();
        return this;
    }

    // Метод для ввода комментария для курьера
    public RentalInformationPage setCommentField(String comment) {
        driver.findElement(commentField).clear();
        driver.findElement(commentField).sendKeys(comment);
        return this;
    }

    // Метод для нажатия на кнопку "Заказать"
    public RentalInformationPage clickOrderButton() {
        driver.findElement(orderButton).click();
        return this;
    }

    // метод для выбора срока аренды
    public RentalInformationPage selectRentalPeriod(String period) {
        clickRentalPeriodDropdown();
        By periodLocator = By.xpath("//div[contains(text(), '" + period + "')]");
        driver.findElement(periodLocator).click();
        return this;
    }
}
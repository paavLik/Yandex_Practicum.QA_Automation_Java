package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;


public class OrderFormPage {

    private final WebDriver driver;

    // Поле для ввода Имени
    private final By nameField = By.xpath(".//input[@placeholder ='* Имя']");
    // Поле для ввода Фамилии
    private final By lastNameField = By.xpath(".//input[@placeholder ='* Фамилия']");
    // Поля для ввода Адреса доставки заказа
    private final By deliveryAddressField = By.xpath(".//input[@placeholder ='* Адрес: куда привезти заказ']");
    // Поле для ввода Станции метро
    private final By metroStationField = By.xpath(".//input[@placeholder ='* Станция метро']");
    // Полле для ввода контактного номера телефона
    private final By phoneNumberField = By.xpath(".//input[@placeholder ='* Телефон: на него позвонит курьер']");

    // Кнопка "Далее"
    private final By nextButton = By.xpath(".//button[contains(text(), 'Далее')]");

    public OrderFormPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод заполнения поля "Имя"
    public OrderFormPage setNameField(String name) {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(nameField));
        driver.findElement(nameField).clear();
        driver.findElement(nameField).sendKeys(name);
        return this;
    }

    // Метод заполнения поля "Фамилия"
    public OrderFormPage setLastNameField(String lastName) {
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);
        return this;
    }

    // Метод заполнения поля "Адрес доставки"
    public OrderFormPage setDeliveryAddressField(String adress) {
        driver.findElement(deliveryAddressField).clear();
        driver.findElement(deliveryAddressField).sendKeys(adress);
        return this;
    }

    public OrderFormPage setMetroStationField(String metro) {
        driver.findElement(metroStationField).clear();
        driver.findElement(metroStationField).sendKeys(metro);
        By metroLocator = By.xpath("//div[contains(text(), '" + metro + "')]");
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(metroLocator));
        driver.findElement(metroLocator).click();
        return this;
    }


    // Метод заполнения поля "Телефон"
    public OrderFormPage setPhoneNumberField(String phone) {
        driver.findElement(phoneNumberField).clear();
        driver.findElement(phoneNumberField).sendKeys(phone);
        return this;
    }

    // объединенный метод заполненеия всех полей формы заказа
    public OrderFormPage setOrderFormFields(String name, String lastName, String ares, String metro, String phone) {
        setNameField(name);
        setLastNameField(lastName);
        setDeliveryAddressField(ares);
        setMetroStationField(metro);
        setPhoneNumberField(phone);
        return this;
    }

    // Метод нажатия кнопки "Далее"
    public OrderFormPage clickNextButton() {
        driver.findElement(nextButton).click();
        return this;
    }
}

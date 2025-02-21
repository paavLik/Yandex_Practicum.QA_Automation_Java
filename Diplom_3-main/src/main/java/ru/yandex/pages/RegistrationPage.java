package ru.yandex.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private final WebDriver webDriver;
    // Локатор полей ввода
    private final By inputFields = By.xpath(".//form[starts-with(@class, 'Auth_form')]//fieldset//div[@class=" +
            "'input__container']//input");
    // Локатор кнопки регистрации
    private final By registerButton = By.xpath(".//form[starts-with(@class, 'Auth_form')]/button");
    // Локатор сообщения об ошибке пароля
    private final By passwordErrorMsg = By.xpath(".//*[text()='Некорректный пароль']");
    // Локатор сообщения об ошибке существующего пользователя
    private final By userExistsErrorMsg = By.xpath(".//*[text()='Такой пользователь уже существует']");
    // Локатор заголовка
    private final By title = By.xpath(".//main//h2");
    // Локатор ссылки авторизации
    private final By authLink = By.xpath(".//a[starts-with(@class,'Auth_link')]");
    // Локатор модального оверлея
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div[starts-with" +
            "(@class, 'Modal_modal')]");

    public RegistrationPage(WebDriver driver) {
        webDriver = driver;
    }

    public WebElement getAuthLinkElement() {
        return webDriver.findElement(authLink);
    }

    @Step("Заполнение имени")
    public void enterName(String name) {
        webDriver.findElements(inputFields).get(0).sendKeys(name);
    }

    @Step("Заполнение email")
    public void enterEmail(String email) {
        webDriver.findElements(inputFields).get(1).sendKeys(email);
    }

    @Step("Заполнение пароля")
    public void enterPassword(String password) {
        webDriver.findElements(inputFields).get(2).sendKeys(password);
    }

    @Step("Нажатие на кнопку регистрации")
    public void clickRegisterButton() {
        waitButtonIsClickable();
        webDriver.findElement(registerButton).click();
    }

    @Step("Ожидание кликабельности кнопки")
    private void waitButtonIsClickable() {
        new WebDriverWait(webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOf(webDriver.findElement(modalOverlay)));
    }

    @Step("Ожидание завершения отправки формы")
    public void waitFormSubmitted(String expectedTitle) {
        new WebDriverWait(webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.textToBe(title, expectedTitle));
    }

    @Step("Ожидание видимости сообщения об ошибке Некорректный пароль")
    public void waitErrorIsVisible() {
        new WebDriverWait(webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOf(webDriver.findElement(passwordErrorMsg)));
    }

    @Step("Получение сообщения об ошибке Некорректный пароль")
    public String getPasswordErrorMsg() {
        return webDriver.findElement(passwordErrorMsg).getText();
    }

    @Step("Ожидание видимости сообщения об ошибке Такой пользователь уже существует")
    public void waitErrorUserExistsIsVisible() {
        new WebDriverWait(webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOf(webDriver.findElement(userExistsErrorMsg)));
    }

    @Step("Получение сообщения об ошибке Такой пользователь уже существует")
    public String getUserExistsErrorMsg() {
        return webDriver.findElement(userExistsErrorMsg).getText();
    }

    @Step("Нажатие на ссылку авторизации")
    public void clickAuthLink() {
        waitButtonIsClickable();
        webDriver.findElement(authLink).click();
    }

    @Step("Прокрутка вниз до элемента")
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}

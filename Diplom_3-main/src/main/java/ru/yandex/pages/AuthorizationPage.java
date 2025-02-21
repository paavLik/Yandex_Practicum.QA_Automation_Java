package ru.yandex.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthorizationPage {
    private final WebDriver driver;
    // Локатор заголовка страницы
    private final By header = By.tagName("h1");
    // Локатор полей ввода
    private final By inputFields = By.xpath(".//form[starts-with(@class, 'Auth_form')]//fieldset//div[@class=" +
            "'input__container']//input");
    // Локатор кнопки Войти
    private final By authButton = By.xpath(".//form[starts-with(@class, 'Auth_form')]/button");
    // Локатор заголовка Вход
    private final By title = By.xpath(".//main//h2");
    // Локатор модального оверлея
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with" +
            "(@class, 'Modal_modal_overlay')]");

    public AuthorizationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Получение заголовка страницы")
    public String getPageTitle() {
        return driver.findElement(title).getText();
    }

    @Step("Заполнение поля Email")
    public void enterEmail(String email) {
        driver.findElements(inputFields).get(0).sendKeys(email);
    }

    @Step("Заполнение поля Пароль")
    public void enterPassword(String password) {
        driver.findElements(inputFields).get(1).sendKeys(password);
    }

    @Step("Нажатие на кнопку Авторизации")
    public void clickAuthorizationButton() {
        waitUntilButtonClickable();
        driver.findElement(authButton).click();
    }

    @Step("Ожидание кликабельности кнопки")
    public void waitUntilButtonClickable() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOf(driver.findElement(modalOverlay)));
    }

    @Step("Ожидание завершения отправки формы")
    public void waitForFormSubmission() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    @Step("Ожидание видимости формы авторизации")
    public void waitForAuthFormVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(40))
                .until(ExpectedConditions.textToBe(title, "Вход"));
    }
}

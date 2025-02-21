package ru.yandex.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PasswordRecoveryPage {
    private final WebDriver driver;
    // Локатор ссылки Войти
    private final By authLink = By.xpath(".//a[starts-with(@class,'Auth_link')]");
    // Локатор модального оверлея
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div[starts-with(@class," +
            " 'Modal_modal')]");

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие на ссылку Войти")
    public void clickAuthLink() {
        waitButtonIsClickable();
        driver.findElement(authLink).click();
    }

    @Step("Ожидание кликабельности кнопки")
    private void waitButtonIsClickable() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOf(driver.findElement(modalOverlay)));
    }
}
package ru.yandex.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private WebDriver driver;
    // Локатор ссылок в заголовке
    private final By headerLinks = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText')]");
    // Локатор логотипа
    private final By logoLink = By.xpath(".//div[starts-with(@class,'AppHeader_header__logo')]/a");
    // Локатор активной ссылки профиля
    private final By profileNavLink = By.xpath(".//a[contains(@class, 'Account_link_active')]");
    // Локатор кнопки выхода
    private final By logOutLink = By.xpath(".//nav[starts-with(@class, 'Account_nav')]/ul/li/button");
    // Локатор модального оверлея
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with" +
            "(@class, 'Modal_modal_overlay')]");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание видимости формы авторизации")
    public void waitAuthFormVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(profileNavLink));
    }

    @Step("Ожидание кликабельности кнопки")
    public void waitButtonIsClickable() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOf(driver.findElement(modalOverlay)));
    }

    @Step("Нажатие на ссылку конструктора")
    public void clickLinkToConstructor() {
        waitButtonIsClickable();
        driver.findElements(headerLinks).get(0).click();
    }

    @Step("Нажатие на логотип")
    public void clickLinkOnLogo() {
        waitButtonIsClickable();
        driver.findElement(logoLink).click();
    }

    @Step("Нажатие на ссылку выхода")
    public void clickLogoutLink() {
        waitButtonIsClickable();
        driver.findElement(logOutLink).click();
    }
}
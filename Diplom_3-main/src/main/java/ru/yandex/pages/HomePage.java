package ru.yandex.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    // Локатор ссылок в заголовке
    private final By headerLinks = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText')]");
    // Локатор кнопки корзины
    private final By basketButton = By.xpath(".//div[starts-with(@class,'BurgerConstructor_basket_" +
            "_container')]/button");
    // Локатор кнопок ингредиентов
    private final By ingredientsButtons = By.xpath(".//section[starts-with(@class, 'BurgerIngredients" +
            "_ingredients')]/div/div");
    // Локатор заголовков ингредиентов
    private final By ingredientsTitles = By.xpath(".//div[starts-with(@class, 'BurgerIngredients_ingredients_" +
            "_menuContainer')]/h2");
    // Локатор заголовка
    private final By header = By.xpath(".//main//h1");
    // Локатор модального оверлея
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with(@class," +
            " 'Modal_modal_overlay')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие на кнопку авторизации")
    public void clickAuthorizationButton() {
        waitButtonIsClickable();
        driver.findElement(basketButton).click();
    }

    @Step("Ожидание кликабельности кнопки")
    public void waitButtonIsClickable() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOf(driver.findElement(modalOverlay)));
    }

    @Step("Ожидание видимости заголовка")
    public void waitHeaderIsVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    @Step("Ожидание прокрутки до ингредиентов")
    private void waitIngredientsScrolled(int navNumber) {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(driver -> {
                            return driver.findElements(ingredientsTitles).get(navNumber).getLocation().getY() == 243;
                        }
                );
    }

    @Step("Получение текста кнопки корзины")
    public String getBasketButtonText() {
        return driver.findElement(basketButton).getText();
    }

    @Step("Нажатие на ссылку профиля")
    public void clickLinkToProfile() {
        waitButtonIsClickable();
        driver.findElements(headerLinks).get(2).click();
    }

    @Step("Получение ожидаемой позиции заголовка ингредиента")
    public int getIngredientTitleExpectedLocation() {
        return Integer.valueOf(driver.findElements(ingredientsButtons).get(0).getLocation().getY()
                + driver.findElements(ingredientsButtons).get(0).getSize().getHeight()
        );
    }

    @Step("Нажатие на кнопку Булок")
    public void clickBunsButton() {
        waitButtonIsClickable();
        driver.findElements(ingredientsButtons).get(0).click();
        waitIngredientsScrolled(0);
    }

    @Step("Нажатие на кнопку Соусов")
    public void clickToppingsButton() {
        waitButtonIsClickable();
        driver.findElements(ingredientsButtons).get(1).click();
        waitIngredientsScrolled(1);
    }

    @Step("Нажатие на кнопку Начинок")
    public void clickFillingsButton() {
        waitButtonIsClickable();
        driver.findElements(ingredientsButtons).get(2).click();
        waitIngredientsScrolled(2);
    }

    @Step("Получение позиции булок")
    public int getBunsLocation() {
        return Integer.valueOf(driver.findElements(ingredientsTitles).get(0).getLocation().getY());
    }

    @Step("Получение позиции соусов")
    public int getToppingsLocation() {
        return Integer.valueOf(driver.findElements(ingredientsTitles).get(1).getLocation().getY());
    }

    @Step("Получение позиции начинок")
    public int getFillingsLocation() {
        return Integer.valueOf(driver.findElements(ingredientsTitles).get(2).getLocation().getY());
    }
}
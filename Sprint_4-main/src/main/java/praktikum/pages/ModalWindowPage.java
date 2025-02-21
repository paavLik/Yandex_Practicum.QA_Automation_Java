package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class ModalWindowPage {

    private final WebDriver driver;

    // кнопка "Да" в модальном окне подтверждения заказа
    private final By yesButton = By.xpath(".//button[contains(text(), 'Да')]");

    // модальное окно оформленного заказа
    private final By modalOrderRegisterWindow = By.xpath(".//div[contains(text(), 'Заказ оформлен')]");

    public ModalWindowPage(WebDriver driver) {
        this.driver = driver;
    }

    // метод проверки доступности и нажатия на кнопку "Да" в окне подтверждения заказа
    public ModalWindowPage clickYesButton() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(yesButton));
        driver.findElement(yesButton).click();
        return this;
    }

    // метод проверки того, что появилось всплывающее окно с сообщением об успешном оформлении заказа.
    public ModalWindowPage checkRegisteredOrderSuccessfully() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(modalOrderRegisterWindow));

        assertTrue("Модальное окно для зарегистрированного заказа не отображается",
                driver.findElement(modalOrderRegisterWindow).isDisplayed());
        return this;
    }
}

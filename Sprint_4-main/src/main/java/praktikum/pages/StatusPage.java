package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import static org.junit.Assert.assertTrue;

import java.time.Duration;

public class StatusPage {
    private final WebDriver driver;

    // локатор для изображения с информацией об отсутствующем заказе
    private final By orderNotFound = By.xpath(".//img[@alt = 'Not found']");

    public StatusPage(WebDriver driver) {
        this.driver = driver;
    }

    // метод проверяет наличие информации об отсутствующем заказе
    public StatusPage checkOrderNotFound() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(orderNotFound));

        assertTrue("Изображение 'Заказ не найден' должно отображаться",
                driver.findElement(orderNotFound).isDisplayed());
        return this;
    }
}

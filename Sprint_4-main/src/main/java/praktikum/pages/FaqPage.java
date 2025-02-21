package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.EnvConfig;

import java.time.Duration;

public class FaqPage {

    private final WebDriver driver;

    // элемент формы «Вопросы о важном»
    private final By homeFaq = By.xpath("//div[contains(text(), 'Вопросы о важном')]");
    // локатор для кнопок из раздела «Вопросы о важном»
    private final String headingPrefix = "accordion__heading-";
    // локатор для текстов с ответами на вопросы из раздела «Вопросы о важном»
    private final String textOfFaqList = "accordion__panel-";

    public FaqPage(WebDriver driver) {
        this.driver = driver;
    }

    // метод скролла до формы «Вопросы о важном»
    public void scrollToHomeFaq() {
        WebElement homeFaqElement = driver.findElement(homeFaq);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", homeFaqElement);
    }

    // метод клика на вопрос из раздела «Вопросы о важном»
    public void clickOnHeading(String id) {
        By headingLocator = By.id(headingPrefix + id);
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(headingLocator));

        driver.findElement((headingLocator)).click();
    }

    // метод получения ответа
    public String getTextAnswer(String id) {
        By answerElement = By.id(textOfFaqList + id);
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(answerElement));

        return driver.findElement(answerElement).getText();
    }
}

package praktikum;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.pages.MainPage;

import static praktikum.EnvConfig.BASE_URL;

public class LogoScooterRedirectTest {

    @Rule
    public DriverRule driverRule = new DriverRule();

    @Test
    public void testLogoRedirect() {
        WebDriver driver = driverRule.getDriver();

        MainPage mainPage = new MainPage(driver)
                .open()
                .clickOrderUp()
                .clickLogoScooter();

        Assert.assertEquals("Не удалось перейти на главную страницу \"Самоката\" после нажатия на логотип",
                BASE_URL, driver.getCurrentUrl());
    }
}


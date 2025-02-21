package tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.pages.HomePage;
import utils.DriverRule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.api.Config.BASE_URL;

@DisplayName("Тесты Главной Страницы")
@RunWith(Parameterized.class)
public class HomePageValidationTests {
    @Rule
    public DriverRule driverRule = new DriverRule();

    private WebDriver driver;
    private HomePage homePage;
    private final String browserName;

    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Object[][] initParams() {
        return new Object[][]{
                {"chrome"},
                {"firefox"}
        };
    }

    public HomePageValidationTests(String browserName) {
        this.browserName = browserName;
    }

    @Before
    @Step("Запуск браузера")
    public void setUp() {
        System.setProperty("browser", browserName);
        driver = driverRule.getDriver();
        driver.get(BASE_URL);
        homePage = new HomePage(driver);
    }

    @Test
    @Step("Нажатие на вкладку Булки")
    @DisplayName("Проверка работы вкладки Булки в разделе с ингредиентами")
    public void testNavBuns() {
        int expectedLocation = homePage.getIngredientTitleExpectedLocation();

        homePage.clickToppingsButton();
        homePage.clickBunsButton();

        assertThat(
                "Ингредиенты не проскроллились до булок",
                homePage.getBunsLocation(),
                equalTo(expectedLocation)
        );
    }

    @Test
    @Step("Нажатие на вкладку Соусы")
    @DisplayName("Проверка работы вкладки Соусы в разделе с ингредиентами")
    public void testNavToppings() {
        int expectedLocation = homePage.getIngredientTitleExpectedLocation();

        homePage.clickToppingsButton();

        assertThat(
                "Ингредиенты не проскроллились до соусов",
                homePage.getToppingsLocation(),
                equalTo(expectedLocation)
        );
    }

    @Test
    @Step("Нажатие на вкладку Начинки")
    @DisplayName("Проверка работы вкладки Начинки в разделе с ингредиентами")
    public void testNavFillings() {
        int expectedLocation = homePage.getIngredientTitleExpectedLocation();

        homePage.clickFillingsButton();

        assertThat(
                "Ингредиенты не проскроллились до начинок",
                homePage.getFillingsLocation(),
                equalTo(expectedLocation)
        );
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
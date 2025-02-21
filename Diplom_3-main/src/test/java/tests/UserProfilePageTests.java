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
import ru.yandex.api.ApiHandler;
import ru.yandex.pages.AuthorizationPage;
import ru.yandex.pages.HomePage;
import ru.yandex.pages.ProfilePage;
import utils.DriverRule;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.api.Config.URL_LOGIN_PAGE;
import static ru.yandex.api.Config.BASE_URL;

@DisplayName("Тесты Личного Кабинета Пользователя")
@RunWith(Parameterized.class)
public class UserProfilePageTests {
    @Rule
    public DriverRule driverRule = new DriverRule();

    private WebDriver driver;
    private AuthorizationPage authorizationPage;
    private HomePage homePage;
    private ProfilePage profilePage;
    private String name, email, password;
    private ApiHandler apiHandler;
    private final String browserName;

    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Object[][] initParams() {
        return new Object[][]{
                {"chrome"},
                {"firefox"}
        };
    }

    public UserProfilePageTests(String browserName) {
        this.browserName = browserName;
    }

    @Before
    @Step("Запуск браузера и подготовка тестовых данных")
    public void setUp() {
        System.setProperty("browser", browserName);
        driver = driverRule.getDriver();
        driver.get(BASE_URL);

        authorizationPage = new AuthorizationPage(driver);
        homePage = new HomePage(driver);
        profilePage = new ProfilePage(driver);

        name = "profileuser";
        email = "profile_" + UUID.randomUUID() + "@example.com";
        password = "profilepass_" + UUID.randomUUID();

        apiHandler = new ApiHandler();
        apiHandler.createUser(name, email, password);
    }

    @Step("Процесс авторизации пользователя")
    private void authorizeUser() {
        authorizationPage.enterEmail(email);
        authorizationPage.enterPassword(password);
        authorizationPage.clickAuthorizationButton();
        authorizationPage.waitForFormSubmission();
    }

    @Step("Переход в личный кабинет")
    private void goToProfile() {
        driver.get(URL_LOGIN_PAGE);
        authorizationPage.waitForAuthFormVisible();
        authorizeUser();
        homePage.clickLinkToProfile();
        profilePage.waitAuthFormVisible();
    }

    @Test
    @DisplayName("Проверка перехода по клику на «Личный кабинет»")
    public void testProfileLinkNavigation() {
        goToProfile();

        assertThat(
                "Некорректный URL страницы Личного кабинета",
                driver.getCurrentUrl(),
                containsString("/account/profile")
        );
    }

    @Test
    @DisplayName("Проверка перехода из личного кабинета по клику на «Конструктор»")
    public void testConstructorLinkNavigation() {
        goToProfile();
        profilePage.clickLinkToConstructor();
        homePage.waitHeaderIsVisible();

        assertThat(
                "Ожидается текст «Оформить заказ» на кнопке корзины",
                homePage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Проверка перехода из личного кабинета по клику на логотип Stellar Burgers")
    public void testLogoLinkNavigation() {
        goToProfile();
        profilePage.clickLinkOnLogo();
        homePage.waitHeaderIsVisible();

        assertThat(
                "Ожидается текст «Оформить заказ» на кнопке корзины",
                homePage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Проверка выхода из личного кабинета по клику на кнопку Выйти")
    public void testLogoutLinkNavigation() {
        goToProfile();
        profilePage.clickLogoutLink();
        authorizationPage.waitForAuthFormVisible();

        assertThat(
                "Некорректный URL страницы Авторизации",
                driver.getCurrentUrl(),
                containsString("/login")
        );
    }

    @After
    @Step("Закрытие браузера и очистка данных")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        apiHandler.deleteTestUser(email, password);
    }
}
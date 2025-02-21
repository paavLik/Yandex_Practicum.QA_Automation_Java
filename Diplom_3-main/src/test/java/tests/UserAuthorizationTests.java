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
import ru.yandex.pages.PasswordRecoveryPage;
import ru.yandex.pages.RegistrationPage;
import utils.DriverRule;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.api.Config.*;

@DisplayName("Тесты Авторизации Пользователя")
@RunWith(Parameterized.class)
public class UserAuthorizationTests {
    @Rule
    public DriverRule driverRule = new DriverRule();

    private WebDriver driver;
    private AuthorizationPage authorizationPage;
    private HomePage homePage;
    private RegistrationPage registrationPage;
    private PasswordRecoveryPage passwordRecoveryPage;
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

    public UserAuthorizationTests(String browserName) {
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
        registrationPage = new RegistrationPage(driver);
        passwordRecoveryPage = new PasswordRecoveryPage(driver);

        name = "testuser";
        email = "test_" + UUID.randomUUID() + "@example.com";
        password = "testpass_" + UUID.randomUUID();

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

    @Test
    @DisplayName("Вход через кнопку на главной странице")
    public void testAuthorizationFromMainPage() {
        homePage.clickAuthorizationButton();
        authorizationPage.waitForAuthFormVisible();
        authorizeUser();

        assertThat(
                "Ожидается текст «Оформить заказ» на кнопке корзины",
                homePage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void testAuthorizationFromProfileLink() {
        homePage.clickLinkToProfile();
        authorizationPage.waitForAuthFormVisible();
        authorizeUser();

        assertThat(
                "Ожидается текст «Оформить заказ» на кнопке корзины",
                homePage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void testAuthorizationFromRegistrationForm() {
        driver.get(URL_REGISTER_PAGE);
        registrationPage.scrollToElement(registrationPage.getAuthLinkElement());
        registrationPage.clickAuthLink();
        authorizationPage.waitForAuthFormVisible();
        authorizeUser();

        assertThat(
                "Ожидается текст «Оформить заказ» на кнопке корзины",
                homePage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void testAuthorizationFromPasswordRecoveryForm() {
        driver.get(URL_RECOVERY_PASSWORD_PAGE);
        passwordRecoveryPage.clickAuthLink();
        authorizationPage.waitForAuthFormVisible();
        authorizeUser();

        assertThat(
                "Ожидается текст «Оформить заказ» на кнопке корзины",
                homePage.getBasketButtonText(),
                equalTo("Оформить заказ")
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

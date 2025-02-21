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
import ru.yandex.pages.RegistrationPage;
import utils.DriverRule;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.api.Config.URL_REGISTER_PAGE;

@DisplayName("Тесты Регистрации Пользователя")
@RunWith(Parameterized.class)
public class UserRegistrationTests {
    @Rule
    public DriverRule driverRule = new DriverRule();

    private WebDriver driver;
    private RegistrationPage registrationPage;
    private String email, name, password;
    private final String browserName;
    private ApiHandler apiHandler;

    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Object[][] initParams() {
        return new Object[][]{
                {"chrome"},
                {"firefox"}
        };
    }

    public UserRegistrationTests(String browserName) {
        this.browserName = browserName;
    }

    @Before
    @Step("Запуск браузера и подготовка тестовых данных")
    public void setUp() {
        System.setProperty("browser", browserName);
        driver = driverRule.getDriver();
        driver.get(URL_REGISTER_PAGE);
        registrationPage = new RegistrationPage(driver);

        apiHandler = new ApiHandler();
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void testSuccessfulRegistration() {
        email = "register_" + UUID.randomUUID() + "@example.com";
        name = "registeruser";
        password = "registerpass_" + UUID.randomUUID();

        registrationPage.enterEmail(email);
        registrationPage.enterName(name);
        registrationPage.enterPassword(password);
        registrationPage.clickRegisterButton();
        registrationPage.waitFormSubmitted("Вход");
        checkFormReload();
    }

    @Test
    @DisplayName("Регистрация с коротким паролем")
    public void testRegistrationWithShortPassword() {
        email = "register_" + UUID.randomUUID() + "@example.com";
        name = "registeruser";
        password = "short";

        registrationPage.enterEmail(email);
        registrationPage.enterName(name);
        registrationPage.enterPassword(password.substring(0, 3));
        registrationPage.clickRegisterButton();
        registrationPage.waitErrorIsVisible();
        checkErrorMessage();
    }

    @Test
    @DisplayName("Регистрация существующего пользователя")
    public void testRegistrationOfExistingUser() {
        email = "newUser" + UUID.randomUUID() + "@example.com";
        name = "userregister";
        password = "userregisterpass_" + UUID.randomUUID();

        // Создание пользователя для проверки повторной регистрации
        apiHandler.createUser(name, email, password);

        driver.get(URL_REGISTER_PAGE);
        registrationPage.enterEmail(email);
        registrationPage.enterName(name);
        registrationPage.enterPassword(password);
        registrationPage.clickRegisterButton();
        registrationPage.waitErrorUserExistsIsVisible();

        assertThat(
                "Ожидается текст «Такой пользователь уже существует»",
                registrationPage.getUserExistsErrorMsg(),
                containsString("Такой пользователь уже существует")
        );
    }

    @Step("Проверка перезагрузки формы регистрации")
    private void checkFormReload() {
        assertThat(
                "Форма регистрации не перезагрузилась",
                driver.getCurrentUrl(),
                containsString("/login")
        );
    }

    @Step("Проверка появления сообщения об ошибке")
    private void checkErrorMessage() {
        assertThat(
                "Некорректное сообщение об ошибке",
                registrationPage.getPasswordErrorMsg(),
                equalTo("Некорректный пароль")
        );
    }

    @After
    @Step("Закрытие браузера и очистка данных")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        // Удаление тестового пользователя, если он был создан
        if (email != null && password != null) {
            apiHandler.deleteTestUser(email, password);
        }
    }
}

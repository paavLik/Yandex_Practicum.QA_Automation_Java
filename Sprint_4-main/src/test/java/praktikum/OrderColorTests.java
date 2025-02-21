package praktikum;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import praktikum.pages.MainPage;
import praktikum.pages.ModalWindowPage;
import praktikum.pages.OrderFormPage;
import praktikum.pages.RentalInformationPage;

import static praktikum.constants.Data.*;

@RunWith(Parameterized.class)
public class OrderColorTests {

    @Rule
    public DriverRule driverRule = new DriverRule();

    private final String color;

    public OrderColorTests(String color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] colorData() {
        return new Object[][]{
                {"grey"},
                {"black"},
        };
    }

    @Test
    public void successOrderWithColorTest() {
        WebDriver driver = driverRule.getDriver();

        MainPage mainPage = new MainPage(driver)
                .open()
                .clickApproval()
                .clickOrderUp();

        OrderFormPage orderFormPage = new OrderFormPage(driver)
                .setOrderFormFields(NAME, LAST_NAME, ADDRESS, METRO, PHONE)
                .clickNextButton();

        RentalInformationPage rentalInformationPage = new RentalInformationPage(driver)
                .setDeliveryDateField(DATE)
                .selectRentalPeriod(ONE_DAY)
                .selectColor(color)
                .setCommentField(COMMENT)
                .clickOrderButton();

        ModalWindowPage modalWindowPage = new ModalWindowPage(driver)
                .clickYesButton()
                .checkRegisteredOrderSuccessfully();
    }
}

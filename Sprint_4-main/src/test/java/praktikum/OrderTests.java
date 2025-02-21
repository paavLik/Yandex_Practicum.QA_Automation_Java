package praktikum;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.pages.*;

import static praktikum.constants.Data.*;


public class OrderTests {

    @Rule
    public DriverRule driverRule = new DriverRule();

    @Test
    public void successfulScooterOrderUpTest() {
        WebDriver driver = driverRule.getDriver();

        MainPage maim = new MainPage(driver)
                .open()
                .clickApproval()
                .clickOrderUp();

        OrderFormPage orderFormPage = new OrderFormPage(driver)
                .setOrderFormFields(NAME, LAST_NAME, ADDRESS, METRO, PHONE)
                .clickNextButton();

        RentalInformationPage rent = new RentalInformationPage(driver)
                .setDeliveryDateField(DATE)
                .selectRentalPeriod(ONE_DAY)
                .selectColor(GREY)
                .setCommentField(COMMENT)
                .clickOrderButton();

        ModalWindowPage modal = new ModalWindowPage(driver)
                .clickYesButton()
                .checkRegisteredOrderSuccessfully();
    }

    @Test
    public void successfulScooterOrderDownTest() {
        WebDriver driver = driverRule.getDriver();

        MainPage maim = new MainPage(driver)
                .open()
                .clickApproval()
                .clickOrderDown();

        OrderFormPage orderFormPage = new OrderFormPage(driver)
                .setOrderFormFields(NAME, LAST_NAME, ADDRESS, METRO, PHONE)
                .clickNextButton();

        RentalInformationPage rent = new RentalInformationPage(driver)
                .setDeliveryDateField(DATE)
                .selectRentalPeriod(SEVEN_DAYS)
                .selectColor(BLACK)
                .setCommentField(COMMENT)
                .clickOrderButton();

        ModalWindowPage modal = new ModalWindowPage(driver)
                .clickYesButton()
                .checkRegisteredOrderSuccessfully();
    }

    @Test
    public void checkOrderNotFoundTest() {
        WebDriver driver = driverRule.getDriver();
        MainPage page = new MainPage(driver)
                .open()
                .clickOrderStatus()
                .enterOrderNumber(INVALID_ORDER_NUMBER)
                .clickGo();

        StatusPage statusPage = new StatusPage(driver)
                .checkOrderNotFound();
    }
}

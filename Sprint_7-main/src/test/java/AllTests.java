import courier.CourierCreateTest;
import courier.CouriereDeleteTest;
import courier.CredentialsTest;
import order.CreateOrderParamTest;
import order.GetOrderListTest;
import order.GetOrderTest;
import order.GetOrderTrackParamTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        CourierCreateTest.class,
        CouriereDeleteTest.class,
        CredentialsTest.class,
        CreateOrderParamTest.class,
        GetOrderListTest.class,
        GetOrderTest.class,
        GetOrderTrackParamTest.class
})

public class AllTests {
}


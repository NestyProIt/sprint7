package TestOrder;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.scooter.services.Order.OrderStep;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {
    private final OrderStep orderStep = new OrderStep();


    @Test
    @DisplayName("Получить список заказов")
    @Description("Возвращается список заказов")
    public void getOrderGetList() {
        ValidatableResponse response = orderStep.getListOrders();
        //Проверяем, что статус код 200 и поле "orders" не пустое
        response.assertThat().log().all()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}



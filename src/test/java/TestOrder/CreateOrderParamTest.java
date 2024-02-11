package TestOrder;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.scooter.services.Order.OrderCreate;
import ru.scooter.services.Order.OrderStep;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateOrderParamTest {
    private OrderStep orderStep = new OrderStep();
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
    private String trackOrder;

    public CreateOrderParamTest(String firstName, String lastName,
                                String address, String metroStation,
                                String phone,
                                int rentTime, String deliveryDate,
                                String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката {8}")

    public static Object[][] getValueOrderCreate() {
        return new Object[][]{
                {"Иванушка", "Иванушкаивануш", "Улица Мичуринская, д. 11", "Сокольники", "899988877755", 1, "2024-02-14", "Комментарий", List.of("Black")},
                {"Иванушка", "Иванушкаивануш", "Улица Мичуринская, д. 11", "Сокольники", "899988877755", 2, "2024-02-14", "Комментарий", List.of("Gray")},
                {"Иванушка", "Иванушкаивануш", "Улица Мичуринская, д. 11", "Сокольники", "899988877755", 3, "2024-02-14", "Комментарий", List.of("Black", "Gray")},
                {"Иванушка", "Иванушкаивануш", "Улица Мичуринская, д. 11", "Сокольники", "899988877755", 1, "2024-02-14", "Комментарий", List.of()},

        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void checkOrderCreate() {
        OrderCreate orderCreate = new OrderCreate(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse responseOrder = orderStep.createNewOrder(orderCreate);
        //Проверяем, что статус код равен 201 и поле "track" не пустое
        responseOrder.assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
        //Получаем ID заказа
        trackOrder = responseOrder.extract().path("track").toString();
        ValidatableResponse responseTrack = orderStep.getOrderTrack(trackOrder);
        //Проверяем, что статус код равен 200 и возвращаются данные о заказе:  выбран/не выбран цвет самокат
        responseTrack.assertThat()
                .statusCode(200)
                .and()
                .body("order.color", equalTo(color));

    }

    @After
    //очистка данных после теста
    public void cleanUp() {
        orderStep.cancelOrder(trackOrder).log().all();
    }
}
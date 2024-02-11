package TestOrder;

import io.qameta.allure.Description;
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
public class GetOrderTrackTest {
    private  OrderStep orderStep = new OrderStep();
    private  String firstName;
    private  String lastName;
    private String address;
    private  String metroStation;
    private String phone;
    private  int rentTime;
    private  String deliveryDate;
    private  String comment;
    private  List<String> color;

    private String trackOrder;


    public GetOrderTrackTest(String firstName, String lastName,
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

    @Parameterized.Parameters(name = "Имя {0}, Фамилия {1}, Адрес {2}, Станция метро {3}, Телефон {4}, " +
            "Дата доставки {5}, Срок аренды {6},Комментарий {7}, Цвет самоката {8}")


    public static Object[][] getDataOrderCreate() {
        return new Object[][]{
                {"Иванушка", "Иванушкаивануш", "Улица Мичуринская, д. 11", "Сокольники", "899988877755", 1, "2024-02-14T00:00:00.000Z", "Комментарий", List.of("Black")},};
    }


    @Test
    @DisplayName("Получить заказ по его номеру")
    @Description("Успешный запрос возвращает объект с заказом")
    public void checkOrderGetTrack() {
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
        //Проверяем, что статус код равен 200 и возвращаются данные созданного заказа
        responseTrack.assertThat()
                .statusCode(200)
                .and()
                .body("order.firstName", equalTo(firstName))
                .body("order.lastName", equalTo(lastName))
                .body("order.metroStation", equalTo(metroStation))
                .body("order.phone", equalTo(phone))
                .body("order.rentTime", equalTo(rentTime))
                .body("order.deliveryDate", equalTo(deliveryDate))
                .body("order.comment", equalTo(comment))
                .body("order.firstName", equalTo(firstName))
                .body("order.color", equalTo(color));
    }


  @After
    //очистка данныхх после теста
    public void cleanUp() {
        orderStep.cancelOrder(trackOrder).log().all();
    }
}
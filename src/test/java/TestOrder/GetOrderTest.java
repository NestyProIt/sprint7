package TestOrder;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.scooter.services.Order.OrderStep;

import static org.hamcrest.Matchers.equalTo;

public class GetOrderTest {

    OrderStep orderStep = new OrderStep();
    @Test
    @DisplayName("Запрос с несуществующим номером заказа")
    @Description("Запрос с несуществующим номером заказа возвращает ошибку")
    public void checkOrderGetTrackWithNotRegistered() {
        //Отправляем запрос с несуществующим номером заказа
        String trackOrder = "0";
        ValidatableResponse response = orderStep.getOrderTrack(trackOrder);
        //Проверяем, что статус код равен 404 и возвращается ожидаемое сообщение
        response.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Заказ не найден"));
    }

    @Test
    @DisplayName("Запрос без номера заказа")
    @Description("Запрос без номера заказа возвращает ошибку")
    public void checkOrderGetTrackWithOutId() {
        //Отправляем запрос без номера заказа
        String trackOrder = "";
        ValidatableResponse response = orderStep.getOrderTrack(trackOrder);
        //Проверяем, что статус код равен 400 и возвращается ожидаемое сообщение
        response.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }
}
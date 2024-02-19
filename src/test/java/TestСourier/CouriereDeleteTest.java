package TestСourier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import ru.scooter.services.Courier.CourierCreate;
import ru.scooter.services.Courier.CourierStep;
import ru.scooter.services.Courier.CouriersCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CouriereDeleteTest {
    private CourierStep courierStep;
    private CourierCreate courier;
    private String courierId;

    @Before
    public void setUp() {
        courierStep = new CourierStep();
    }


    @Test
    @DisplayName("Удаление курьера")
    @Description("Успешное удаление учетной записи курьера")
    public void deleteCourier() {
        //Создаем курьера с заданными параметрами
        courier = CourierCreate.getDataGeneratorCourier();
        courierStep.createNewCourier(courier);
        //Получаем ID курьера из ответа сервера
        ValidatableResponse setCourierIdResponse = courierStep.setCourierID(CouriersCredentials.getCouriersCredentials(courier));
        courierId = setCourierIdResponse.extract().path("id").toString();
        //Проверяем, что статус код 200 и поле "id" не пустое
        setCourierIdResponse.assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
        //Удаляем курьера и проверяем,что статус код 200 и поле "ok" равно true
        ValidatableResponse courierResponse = courierStep.deleteCourier(courierId);
        courierResponse.assertThat()
                .statusCode(200)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Удаление курьера без id")
    @Description("Учетная запись курьера удаляется, если параметр id не передан в запросе")
    public void deleteCourierWithOutIdNotRegistered() {
        ValidatableResponse response = courierStep.deleteCourier("");
        //Проверяем, что статус код 404 и возвращается ожидаемое сообщение
        response.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Not Found."));
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим id")
    @Description("Учетная запись курьера удаляется, если в запросе передан несуществующий id")
    public void deleteCourierWithOutId() {
        ValidatableResponse response = courierStep.deleteCourier("0");
        //Проверяем, что статус код 404 и возвращается ожидаемое сообщение
        response.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Курьера с таким id нет."));
    }

}

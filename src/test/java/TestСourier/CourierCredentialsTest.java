package TestСourier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.scooter.services.Courier.CourierCreate;
import ru.scooter.services.Courier.CourierStep;
import ru.scooter.services.Courier.CouriersCredentials;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class CourierCredentialsTest {
    private CourierStep courierStep;
    private CourierCreate courier;
    private String courierId;

    @Before
    public void setUp() {
        courierStep = new CourierStep();
        courier = CourierCreate.getDataGeneratorCourier();
        courierStep.createNewCourier(courier);
    }

    @Test
    @DisplayName("Авторизация курьера cо всеми полями")
    @Description("Курьер может авторизоваться в системе")
    public void checkCourierAuthorizationWithAllParameters() {
        //Получаем ID курьера из ответа сервера
        ValidatableResponse setCourierIdResponse = courierStep.setCourierID(CouriersCredentials.getCouriersCredentials(courier));
        courierId = setCourierIdResponse.extract().path("id").toString();
        //Проверяем, что статус код 200 и поле "id" не пустое
        setCourierIdResponse.assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Курьер не может авторизоваться, если в теле запроса переданы параметры: login - пустое значение")
    public void checkCourierAuthorizationWithOutLogin() {
        //Устанавливаем пустой логин для курьера
        courier.setLogin("");
        //Получаем ID курьера из ответа сервера
        ValidatableResponse setCourierIdResponse = courierStep.setCourierID(CouriersCredentials.getCouriersCredentials(courier));
        //Проверяем, что статус код 400 и возвращается ожидаемое сообщение
        setCourierIdResponse.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Курьер не может авторизоваться, если в теле запроса переданы параметры: password - пустое значение")
    public void checkCourierAuthorizationWithOutPassword() {
        //Устанавливаем пустой пароль для курьера
        courier.setPassword("");
        ValidatableResponse setCourierIdResponse = courierStep.setCourierID(CouriersCredentials.getCouriersCredentials(courier));
        //Проверяем, что статус код 400 и возвращается ожидаемое сообщение
        setCourierIdResponse.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Авторизация курьера без логина, пароля")
    @Description("Курьер не может авторизоваться, если в теле запроса переданы параметры: login, password пустое значение")
    public void checkCourierAuthorizationWithOutLoginPassword() {
        //Устанавливаем пустой логин для курьера
        courier.setLogin("");
        //Устанавливаем пустой пароль для курьера
        courier.setPassword("");
        ValidatableResponse setCourierIdResponse = courierStep.setCourierID(CouriersCredentials.getCouriersCredentials(courier));
        //Проверяем, что статус код 400 и возвращается ожидаемое сообщение
        setCourierIdResponse.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера с несуществующим логином")
    @Description("Курьер не может авторизоваться с несуществующим логином")
    public void checkCourierAuthorizationWithLoginNotRegistered() {
        //Устанавливаем несуществующий логин для курьера
        courier.setLogin("0");
        ValidatableResponse setCourierIdResponse = courierStep.setCourierID(CouriersCredentials.getCouriersCredentials(courier));
        //Проверяем, что статус код 404 и возвращается ожидаемое сообщение
        setCourierIdResponse.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера с несуществующим паролем")
    @Description("Курьер не может авторизоваться с несуществующим паролем")
    public void checkCourierAuthorizationWithPasswordNotRegistered() {
        //Устанавливаем несуществующий пароль для курьера
        courier.setPassword("0");
        ValidatableResponse setCourierIdResponse = courierStep.setCourierID(CouriersCredentials.getCouriersCredentials(courier));
        //Проверяем, что статус код 404 и возвращается ожидаемое сообщение
        setCourierIdResponse.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера с несуществующим логином,паролем")
    @Description("Курьер не может авторизоваться с несуществующим логином, паролем")
    public void checkCourierAuthorizationWithLoginPasswordNotRegistered() {
        //Устанавливаем несуществующий логин для курьера
        courier.setLogin("0");
        //Устанавливаем несуществующий пароль для курьера
        courier.setPassword("0");
        ValidatableResponse setCourierIdResponse = courierStep.setCourierID(CouriersCredentials.getCouriersCredentials(courier));
        //Проверяем, что статус код 404 и возвращается ожидаемое сообщение
        setCourierIdResponse.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }


    @After//очистка данных после теста
    public void deleteCourierAfterTest() {
        if (courierId != null) {
            courierStep.deleteCourier(courierId);
        }
    }

}

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

import static org.hamcrest.Matchers.equalTo;


public class CourierCreateTest {
    private CourierStep courierStep;
    private CourierCreate courier;
    private String courierId;

    @Before
    public void setUp() {
        courierStep = new CourierStep();
        courier = CourierCreate.getDataGeneratorCourier();
    }


    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Учетная запись курьера создается, если в теле запроса переданы все поля, заполненные валидными значениями: login, password, firstName")
    public void createCourierWithAllParameters() {
        //Создаем курьера с заданными параметрами
        ValidatableResponse courierResponse = courierStep.createNewCourier(courier);
        //Проверяем, что статус код 201 и поле "ok" равно true
        courierResponse.assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
        //Получаем ID курьера из ответа сервера
        ValidatableResponse setCourierIdResponse = courierStep.setCourierID(CouriersCredentials.getCouriersCredentials(courier));
        courierId = setCourierIdResponse.extract().path("id").toString();
    }

    @Test
    @DisplayName("Создание учетной записи курьера только с обязательными полями")
    @Description("Учетная запись курьера создается, если в теле запроса переданы заполненными валидными значениями только обязательные поля: login, password")
    public void createCourierWithOutFirstName() {
        //Устанавливаем пустое имя для курьера
        courier.setFirstName("");
        //Создаем курьера с заданными параметрами
        ValidatableResponse courierResponse = courierStep.createNewCourier(courier);
        //Проверяем, что статус код 201 и поле "ok" равно true
        courierResponse.assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
        //Получаем ID курьера из ответа сервера
        ValidatableResponse setCourierIdResponse = courierStep.setCourierID(CouriersCredentials.getCouriersCredentials(courier));
        courierId = setCourierIdResponse.extract().path("id").toString();
    }

    @Test
    @DisplayName("Создание 2-х одинаковых курьеров")
    @Description("Учетная запись курьера не создается, если в базе данных уже есть курьер с таким логином")
    public void createDuplicateLoginCourier() {
        //Создаем курьера с заданными параметрами
        courierStep.createNewCourier(courier);
        ValidatableResponse courierResponse = courierStep.createNewCourier(courier);
        //Проверяем, что статус код 409 и возвращается ожидаемое сообщение
        courierResponse.assertThat().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        //Получаем ID курьера из ответа сервера
        ValidatableResponse responseCredentials = courierStep.setCourierID(CouriersCredentials.getCouriersCredentials(courier));
        courierId = responseCredentials.extract().path("id").toString();

    }

    @Test
    @DisplayName("Создание учетной записи курьера без пароля")
    @Description("Учетная запись курьера не создается, если в теле запроса переданы параметры: password - пустое значение")
    public void createCourierWithOutPassword() {
        //Устанавливаем пустой пароль для курьера
        courier.setPassword("");
        //Создаем курьера с заданными параметрами
        ValidatableResponse courierResponse = courierStep.createNewCourier(courier);
        //Проверяем, что статус код 400 и возвращается ожидаемое сообщение
        courierResponse.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание учетной записи курьера без логина")
    @Description("Учетная запись курьера не создается, если в теле запроса переданы параметры: login - пустое значение")
    public void createCourierWithOutLogin() {
        //Устанавливаем пустой логин для курьера
        courier.setLogin("");
        //Создаем курьера с заданными параметрами
        ValidatableResponse courierResponse = courierStep.createNewCourier(courier);
        //Проверяем, что статус код 400 и возвращается ожидаемое сообщение
        courierResponse.assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание учетной записи курьера без логина, пароля, имени")
    @Description("Учетная запись курьера не создается, если в теле запроса переданы параметры:login, password, firstName- пустое значение")
    public void createCourierWithOutLoginPasswordFirstName() {
        //Устанавливаем пустой логин для курьера
        courier.setLogin("");
        //Устанавливаем пустой пароль для курьера
        courier.setPassword("");
        //Устанавливаем пустое имя для курьера
        courier.setFirstName("");
        //Создаем курьера с заданными параметрами
        ValidatableResponse courierResponse = courierStep.createNewCourier(courier);
        //Проверяем, что статус код 400 и возвращается ожидаемое сообщение
        courierResponse.assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

   @After//очистка данных после теста
    public void deleteCourierAfterTest() {
        if (courierId != null) {
            courierStep.deleteCourier(courierId);
        }
    }
}



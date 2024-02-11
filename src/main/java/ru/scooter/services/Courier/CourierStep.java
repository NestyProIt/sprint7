package ru.scooter.services.Courier;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.scooter.services.Config.Config;

import static io.restassured.RestAssured.given;

public class CourierStep extends Config {
    public static final String POST_COURIER_CREATE = "/api/v1/courier";//API Создание курьера
    public static final String POST_COURIER_LOGIN = "/api/v1/courier/login";//API Логин курьера в системе
    public static final String DELETE_COURIER = "/api/v1/courier/";//API Удаление курьера

    @Step("Создать курьера")
    @Description("POST-запрос")
    public ValidatableResponse createNewCourier(CourierCreate courier) {
    return given().log().all()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(POST_COURIER_CREATE)
                .then();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse setCourierID(CouriersCredentials credentials) {
    return given().log().all()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(POST_COURIER_LOGIN)
                .then().log().all();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(String courierId) {
        return given()
                .spec(getSpec())
                .when()
                .delete(DELETE_COURIER + courierId)
                .then().log().all();
    }
}



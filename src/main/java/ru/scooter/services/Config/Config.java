package ru.scooter.services.Config;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Config {

    public static final String BASE_URI_SCOOTER = "http://qa-scooter.praktikum-services.ru";//Базовый URL

    @Step("Метод для создания настроек запроса: тип контента (JSON) и базовый URI")
    protected RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI_SCOOTER)
                .build();
    }
}

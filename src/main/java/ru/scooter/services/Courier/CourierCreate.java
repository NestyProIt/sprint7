package ru.scooter.services.Courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierCreate {

    private String login;
    private String password;
    private String firstName;

    //Конструктор со всеми параметрами
    public CourierCreate(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    //Конструктор без параметров
    public CourierCreate() {
    }

    //Метод создает случайные значения для логина, пароля и имени курьера
    public static CourierCreate getDataGeneratorCourier() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(4);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierCreate(login, password, firstName);
    }

    //Геттеры и сеттеры
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}

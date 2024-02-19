package ru.scooter.services.Courier;


public class CouriersCredentials {

    private String login;
    private String password;

    //Конструктор со всеми параметрами
    public CouriersCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    //Конструктор без параметров
    public CouriersCredentials() {

    }

    //Метод возвращает объект курьера со случайно сгенерированным логином и паролем
    public static CouriersCredentials getCouriersCredentials(CourierCreate courier) {
        return new CouriersCredentials(courier.getLogin(), courier.getPassword());
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
}




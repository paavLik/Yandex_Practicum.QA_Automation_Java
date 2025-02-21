package ru.yandex.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierCreate {
    private String login;
    private String password;
    private String firstName;

    // конструкторы с параметрами и без
    public CourierCreate(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CourierCreate() {
    }

    // метод генерирует рандомные значения для логина, пароля и имени
    public static CourierCreate getGeneratorDataCourier() {
        String login = RandomStringUtils.randomAlphabetic(12);
        String password = RandomStringUtils.randomAlphabetic(6);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierCreate(login, password, firstName);
    }

    // геттеры и сеттеры
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
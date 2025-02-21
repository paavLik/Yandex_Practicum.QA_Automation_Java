package ru.yandex.courier;

public class Credentials {

    private String login;
    private String password;

    //конструкторы со всеми параметрами и без
    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Credentials() {

    }

    //метод возвращает объект курьера с рандомно сгенерированным логином и паролем
    public static Credentials getCredentials(CourierCreate courier) {
        return new Credentials(courier.getLogin(), courier.getPassword());
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
}
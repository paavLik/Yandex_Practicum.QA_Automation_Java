package ru.yandex.api;

import lombok.Getter;
import lombok.Setter;

public class UserInfo {
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private String name;

    public UserInfo() {}

    public UserInfo(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

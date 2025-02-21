package ru.yandex.user;

import java.util.Random;

public abstract class UserDataGenerator {
    private static final Random random = new Random();

    private static int getRandomNumber() {
        return random.nextInt(100000);
    }

    public static User createUniqueUser() {
        return new User("uniqueUser" + getRandomNumber() + "@test.com", "uniquePass" + getRandomNumber(), "UniqueUser" + getRandomNumber());
    }

    public static User createUserWithEmailOnly() {
        return new User("emailOnly" + getRandomNumber() + "@test.com", null, "EmailOnlyUser" + getRandomNumber());
    }

    public static User createUserWithPasswordOnly() {
        return new User(null, "passwordOnly" + getRandomNumber(), "PasswordOnlyUser" + getRandomNumber());
    }
}
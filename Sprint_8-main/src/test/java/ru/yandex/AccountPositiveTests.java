package ru.yandex;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@DisplayName("Позитивные тесты для проверки требований к имени и фамилии владельца карты")

public class AccountPositiveTests {

    @Test
    @DisplayName("Проверка длинны с именем и фамилией удовлетворяющие все условия")
    public void checkNameLengthSuccessTest() {
        String ownerDetails = "Тимоти Шаламе";
        assertThat("Имя и фамилию " + ownerDetails + " можно печатать на карте", new Account(ownerDetails)
                .checkNameToEmboss(), equalTo(true));
    }

    @Test
    @DisplayName("Проверка минимально допустимой длинны с именем и фамилией")
    public void checkMinLengthNameSuccessTest() {
        String ownerDetails = "Т м";
        assertThat("Имя и фамилию " + ownerDetails + " можно печатать на карте, т.к. длинна " +
                "входит в минимально ожидаемый диапазон", new Account(ownerDetails)
                .checkLengthName(), equalTo(true));
    }

    @Test
    @DisplayName("Проверка максимально допустимой длинны с именем и фамилией")
    public void checkMaxLengthNameSuccessTest() {
        String ownerDetails = "Тимоти ШаламеШаламе";
        assertThat("Имя и фамилию " + ownerDetails + " можно печатать на карте, т.к. длинна " +
                "входит в максимально ожидаемый диапазон", new Account(ownerDetails)
                .checkLengthName(), equalTo(true));
    }

    @Test
    @DisplayName("Проверка одного пробела в имени и фамилии")
    public void checkOneSpaceTest() {
        String ownerDetails = "Тимоти ШаламеШаламе";
        assertThat("Имя и фамилию " + ownerDetails + " можно печатать на карте, т.к. содержит " +
                "один пробел", new Account(ownerDetails)
                .checkOneSpace(), equalTo(true));
    }

    @Test
    @DisplayName("Проверка одного пробела в имени и фамилии")
    public void checkSpaceStartOrEndTest() {
        String ownerDetails = "Тимоти ШаламеШаламе";
        assertThat("Имя и фамилию " + ownerDetails + " можно печатать на карте, т.к. " +
                "пробел стоит не в начале и не в конце строки", new Account(ownerDetails)
                .checkSpaceStartOrEnd(), equalTo(true));
    }
}
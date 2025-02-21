package ru.yandex;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Позитивные тесты для проверки требований к имени и фамилии владельца карты")

public class AccountNegativeTests {

    @Test
    @DisplayName("Проверка имени и фамилии с длинной меньше допустимой")
    public void checkMinLengthNameFailedTest() {
        String ownerDetails = "Тм";
        assertThat("Имя и фамилию " + ownerDetails + " нельзя печатать на карте, т.к. длинна " +
                "не входит в минимально ожидаемый диапазон", new Account(ownerDetails)
                .checkLengthName(), equalTo(false));
    }

    @Test
    @DisplayName("Проверка имени и фамилии с длинной больше допустимой")
    public void checkMaxLengthNameFailedTest() {
        String ownerDetails = "Тимоти ШаламеШаламеАБВГД";
        assertThat("Имя и фамилию " + ownerDetails + " нельзя печатать на карте, т.к. длинна " +
                "не входит в максимально ожидаемый диапазон", new Account(ownerDetails)
                .checkLengthName(), equalTo(false));
    }

    @Test
    @DisplayName("Проверка того, что в имени и фамилии есть не один пробел")
    public void checkOneSpaceFailedTest() {
        String ownerDetails = "Тимоти  ШаламеШаламе";
        assertThat("Имя и фамилию " + ownerDetails + " нельзя печатать на карте, т.к. содержит " +
                "не один пробел", new Account(ownerDetails)
                .checkOneSpace(), equalTo(false));
    }

    @Test
    @DisplayName("Проверка того, что имя и фамилия содержит пробел вначале и/или в конце")
    public void checkSpaceStartOrEndFailedTest() {
        String ownerDetails = " Тимоти ШаламеШаламе ";
        assertThat("Имя и фамилию " + ownerDetails + " нельзя печатать на карте, т.к. " +
                "пробел стоит в начале и/или в конце строки", new Account(ownerDetails)
                .checkSpaceStartOrEnd(), equalTo(false));
    }
}

package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LionTests {
    private static final String MALE = "Самец";
    private static final String EXCEPTION_TEXT = "Используйте допустимые значения пола животного - самец или самка";
    private static final String NOT_DEFINED = "Не определен";
    private Lion lion;

    @Mock
    private Feline feline;

    @Test
    public void getKittensTest() throws Exception {
        lion = new Lion(MALE, feline);

        lion.getKittens();
        Mockito.verify(feline).getKittens();
    }

    @Test
    public void testNotDefinedThrowsException() {
        Throwable throwable = assertThrows(Exception.class, () -> lion = new Lion(NOT_DEFINED, feline));
        assertThat(throwable).hasMessage(EXCEPTION_TEXT);
    }

    @Test
    public void getFoodTest() throws Exception {
        Lion lion = new Lion(MALE, feline);
        List<String> expectedFood = List.of("Животные", "Птицы", "Рыба");
        Mockito.when(feline.getFood(Mockito.eq("Хищник"))).thenReturn(expectedFood);
        List<String> actual = lion.getFood();
        assertEquals(expectedFood, actual);
        Mockito.verify(feline, Mockito.times(1)).getFood("Хищник");
    }
}
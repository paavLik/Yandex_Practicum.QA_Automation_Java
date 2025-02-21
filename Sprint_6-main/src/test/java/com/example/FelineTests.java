package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FelineTests {

    @Spy
    private Feline feline;

    @Test
    public void eatMeatTest() throws Exception {
        feline.eatMeat();
        Mockito.verify(feline, Mockito.times(1)).getFood("Хищник");
    }

    @Test
    public void getFamilyTest() {
        String expectedFelineFamilyName = "Кошачьи";
        assertThat("Некорректное название семейства кошачьих",
                feline.getFamily(),
                equalTo(expectedFelineFamilyName)
        );
    }

    @Test
    public void getKittensDefaultIsCorrectTest() {
        int expectedCount = 1;
        assertThat("Некорректное количество котят",
                feline.getKittens(),
                equalTo(expectedCount)
        );
    }

    @Test
    public void getKittensInputCountIsCorrectTest() {
        int expectedCount = 5;
        assertThat("Некорректное количество котят",
                feline.getKittens(expectedCount),
                equalTo(expectedCount)
        );
    }
}
package com.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AnimalParameterizedTests {
    private Animal animal;
    private static final String HERBIVORE_KIND = "Травоядное";
    private static final String PREDATOR_KIND = "Хищник";
    private static final List<String> HERBIVORE_FOODS = List.of("Трава", "Различные растения");
    private static final List<String> PREDATOR_FOODS = List.of("Животные", "Птицы", "Рыба");

    private final String animalKind;
    private final List<String> foods;

    public AnimalParameterizedTests(String animalKind, List<String> foods) {
        this.animalKind = animalKind;
        this.foods = foods;
    }

    @Parameterized.Parameters
    public static Object[][] setParamsForTest() {
        return new Object[][]{
                {HERBIVORE_KIND, HERBIVORE_FOODS},
                {PREDATOR_KIND, PREDATOR_FOODS},
        };
    }

    @Test
    public void getProperFood() throws Exception {
        assertThat("Некорректная еда",
                new Animal().getFood(animalKind),
                equalTo(this.foods)
        );
    }

    @Before
    public void setUp() {
        animal = new Animal();
    }

    @Test
    public void getFoodTest() throws Exception {
        List<String> actual = animal.getFood(animalKind);
        assertEquals("Некорректная еда",
                foods, actual);
    }
}
package com.example;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class AnimalTests {
    private Animal animal;
    private static final String FAMILY = "Существует несколько семейств: заячьи, беличьи, мышиные, кошачьи, псовые, медвежьи, куньи";
    private static final String EXCEPTION_TEXT = "Неизвестный вид животного, используйте значение Травоядное или Хищник";
    private static final String UNKNOWN_KIND_ANIMAL = "Неизвестный вид животного";

    @Before
    public void setUp() {
        animal = new Animal();
    }

    @Test
    public void getFamilyIsCorrectTest() {
        String actual = animal.getFamily();

        assertEquals("Некорректный список семейств", FAMILY, actual);
    }

    @Test
    public void getFoodExceptionTest() {
        Exception exception = assertThrows(Exception.class, () -> animal.getFood(UNKNOWN_KIND_ANIMAL));
        assertEquals(EXCEPTION_TEXT, exception.getMessage());
    }
}
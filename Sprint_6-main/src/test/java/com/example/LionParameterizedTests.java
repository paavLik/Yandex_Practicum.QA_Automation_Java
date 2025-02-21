package com.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LionParameterizedTests {
    private static final String MALE = "Самец";
    private static final String FEMALE = "Самка";
    private Feline feline;
    private final String sex;
    private final boolean hasMane;

    @Before
    public void setUp() {
        feline = Mockito.mock(Feline.class);
    }

    public LionParameterizedTests(String sex, boolean hasMane) {
        this.sex = sex;
        this.hasMane = hasMane;
    }

    @Parameterized.Parameters
    public static Object[][] setSexForLion() {
        return new Object[][]{
                {MALE, true},
                {FEMALE, false}
        };
    }

    @Test
    public void doesHaveManeTest() throws Exception {
        Lion lion = new Lion(sex, feline);
        boolean actual = lion.doesHaveMane();
        assertEquals("Грива есть только у льва", hasMane, actual);
    }
}
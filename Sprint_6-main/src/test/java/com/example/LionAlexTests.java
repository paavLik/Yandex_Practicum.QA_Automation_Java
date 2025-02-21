package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LionAlexTests {
    @Mock
    private Feline feline;
    private LionAlex lionAlex;
    private final List<String> expectedFriends = List.of(
            "зебра Марти",
            "бегемотиха Глория",
            "жираф Мелман"
    );

    @Test
    public void getKittensTest() throws Exception {
        lionAlex = new LionAlex(feline);
        int actual = lionAlex.getKittens();

        int expectedLionChildren = 0;
        assertEquals("У Алкса не может быть детей!",
                expectedLionChildren, actual);
    }

    @Test
    public void getFriendsTest() throws Exception {
        lionAlex = new LionAlex(feline);
        List<String> actual = lionAlex.getFriends();

        assertEquals("Некорректный список друзей",
                expectedFriends, actual);
    }

    @Test
    public void getPlaceOfLivingTest() throws Exception {
        lionAlex = new LionAlex(feline);
        String actual = lionAlex.getPlaceOfLiving();

        String expectedPlace = "Нью-Йоркский зоопарк";
        assertEquals("Некорректный адрес льва",
                expectedPlace, actual);
    }
}
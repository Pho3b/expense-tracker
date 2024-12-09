package com.example.expensetracker.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CategoryIconTest {
    @Test
    public void CategoryIconConstructorTest() {
        CategoryIcon cIcon = new CategoryIcon(1, 2, "icon.png");
        assertEquals(1, cIcon.iconDrawableId);
    }
}
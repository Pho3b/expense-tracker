package com.example.expensetracker.model;

public class CategoryIcon {
    public int iconDrawableId;
    public int categoryId;
    public String description;

    public CategoryIcon(int iconDrawableId, int categoryId, String description) {
        this.iconDrawableId = iconDrawableId;
        this.categoryId = categoryId;
        this.description = description;
    }
}

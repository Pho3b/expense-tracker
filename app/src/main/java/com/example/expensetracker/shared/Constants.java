package com.example.expensetracker.shared;

import android.graphics.drawable.Drawable;

import com.example.expensetracker.shared.enums.CategoryIcon;

import java.util.HashMap;

public class Constants {
    public static final String TRANSACTION_TYPE_EXTRA = "transaction_type";
    public static final String MY_DEBUG_LOG_TAG = "MY_LOGS_TAG";

    public static final HashMap<CategoryIcon, Drawable> categoryIconsDrawable = new HashMap<>();
}

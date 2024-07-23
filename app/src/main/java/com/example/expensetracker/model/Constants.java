package com.example.expensetracker.model;

import com.example.expensetracker.R;
import com.example.expensetracker.model.CategoryIcon;

public class Constants {
    public static final String TRANSACTION_TYPE_EXTRA = "transaction_type";
    public static final String MY_DEBUG_LOG_TAG = "MY_LOGS_TAG";
    public static final String DATE_PICKER_TAG = "datePicker";

    public static final CategoryIcon[] EXPENSE_ICON_MODELS = new CategoryIcon[]{
            new CategoryIcon(R.drawable.ci_groceries, 0, "Groceries"),
            new CategoryIcon(R.drawable.ci_electronics, 1, "Electronics"),
            new CategoryIcon(R.drawable.ci_trip, 2, "Trip"),
            new CategoryIcon(R.drawable.ci_leisure, 3, "Leisure"),
            new CategoryIcon(R.drawable.ci_car, 4, "Car"),
            new CategoryIcon(R.drawable.ci_drinks, 5, "Drinks"),
            new CategoryIcon(R.drawable.ci_gifts, 6, "Gifts"),
            new CategoryIcon(R.drawable.ci_fitness, 7, "Fitness"),
            new CategoryIcon(R.drawable.ci_education, 8, "Education"),
            new CategoryIcon(R.drawable.ci_dresses, 9, "Dresses"),
            new CategoryIcon(R.drawable.ic_menu_slideshow, 10, "TEST"),
    };

    public static final CategoryIcon[] INCOME_ICON_MODELS = new CategoryIcon[]{
            new CategoryIcon(R.drawable.ci_paycheck, 0, "Paycheck"),
            new CategoryIcon(R.drawable.ci_ticket_restaurant, 1, "Ticket"),
    };
}

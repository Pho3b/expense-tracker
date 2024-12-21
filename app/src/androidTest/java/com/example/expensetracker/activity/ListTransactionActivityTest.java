package com.example.expensetracker.activity;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import com.example.expensetracker.R;
import com.example.expensetracker.enumerator.TransactionType;
import com.example.expensetracker.service.Global;


@RunWith(AndroidJUnit4.class)
public class ListTransactionActivityTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.expensetracker", appContext.getPackageName());
    }

    @Test
    public void UiElementsPresenceTest() {
        try (ActivityScenario<?> scenario = ActivityScenario.launch(ListTransactionActivity.class)) {
            assertEquals(Lifecycle.State.RESUMED, scenario.getState());

            onView(withId(R.id.view_wrapper)).check(matches(isDisplayed()));
            onView(withId(R.id.expense_type_btn)).check(matches(isDisplayed()));
            onView(withId(R.id.income_type_btn)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void TransactionTypeSelectionTest() {
        try (ActivityScenario<?> ignored = ActivityScenario.launch(ListTransactionActivity.class)) {
            assertEquals(TransactionType.Expense, Global.selectedTransactionType);
            onView(withId(R.id.income_type_btn)).perform(click());
            assertEquals(TransactionType.Income, Global.selectedTransactionType);
            onView(withId(R.id.expense_type_btn)).perform(click());
            assertEquals(TransactionType.Expense, Global.selectedTransactionType);
        }
    }
}
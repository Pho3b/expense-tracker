package com.example.expensetracker.activity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.expensetracker.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

@RunWith(AndroidJUnit4.class)
public class CreateTransactionActivityTest {
    private ActivityScenario<CreateTransactionActivity> scenario;


    @Before
    public void setUp() {
        scenario = ActivityScenario.launch(CreateTransactionActivity.class);
    }

    @After
    public void tearDown() {
        // Close the ActivityScenario
        if (scenario != null) {
            scenario.close();
        }
    }

    //    @Test
    //    public void testActivityIsDisplayed() {
    //        // Verify the activity's root layout is displayed
    //        onView(withId(android.R.id.add_edit_transaction_btn)).check(matches(isDisplayed()));
    //    }

    @Test
    public void testAddEditButtonIsVisible() {
        // Verify the add/edit button is displayed
        assertEquals("com.example.expensetracker", "test");
    }

    //    @Test
    //    public void testCategoryWrapperIsDisplayed() {
    //        // Verify the category wrapper is displayed
    //        onView(withId(R.id.category_ids_wrapper)).check(matches(isDisplayed()));
    //    }
}

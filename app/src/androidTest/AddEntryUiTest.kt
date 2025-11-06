package com.example.calorielog

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddEntryUiTest {
    @get:Rule val rule = ActivityScenarioRule(AddEntryActivity::class.java)

    @Test fun canSaveEntry() {
        onView(withId(R.id.editFoodName)).perform(typeText("Toast"), closeSoftKeyboard())
        onView(withId(R.id.editCalories)).perform(typeText("200"), closeSoftKeyboard())
        onView(withId(R.id.btnSave)).perform(click())
        onView(withText("Saved")).check(matches(isDisplayed()))
    }
}

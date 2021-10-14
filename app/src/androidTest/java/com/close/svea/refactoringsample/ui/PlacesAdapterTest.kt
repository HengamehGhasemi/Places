package com.close.svea.refactoringsample.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.espresso.contrib.RecyclerViewActions.*
import com.close.svea.refactoringsample.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*


@RunWith(AndroidJUnit4ClassRunner::class)
class PlacesAdapterTest{

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    val LIST_ITEM_IN_TEST = 7


    @Test
    fun test_recyclerview_is_visible(){

        onView(withId(R.id.get_places_button)).perform(click())

        onView(withId(R.id.places_recycler_view)).check(matches(isDisplayed()))

    }

    @Test
    fun test_recyclerview_item_name_and_description(){

        onView(withId(R.id.get_places_button)).perform(click())

        onView(withId(R.id.places_recycler_view)).check(matches(isDisplayed()))

        onView(withId(R.id.places_recycler_view)).perform(scrollToPosition<PlacesAdapter.ViewHolder>(LIST_ITEM_IN_TEST))

        onView(withText("Berliner Kebab")).check(matches(isDisplayed()))

    }
}
package com.close.svea.refactoringsample.ui


import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.close.svea.refactoringsample.R
import com.close.svea.refactoringsample.util.checkTheInternetConnection
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    var connectivity : Boolean = true

    @Before
    fun setup(){

       connectivity = checkTheInternetConnection(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun onCreate() {

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.main_constraintLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.bottom_round_shape_view)).check(matches(isDisplayed()))
        onView(withId(R.id.map_image_view)).check(matches(isDisplayed()))
        onView(withId(R.id.get_places_button)).check(matches(isDisplayed()))
    }

    @Test
    fun onClick() {

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.get_places_button)).perform(click())

        if(connectivity){
            /*
            placesRecyclerView.visibility = View.VISIBLE
            notConnectedMessage.visibility = View.GONE
            getPlacesButton.visibility = View.GONE
            bottomRoundShapeView.visibility = View.GONE
            notConnectedImageView.visibility = View.GONE
            mapImage.visibility = View.GONE
            */
            onView(withId(R.id.places_recycler_view)).check(matches(isDisplayed()))
            onView(withId(R.id.not_connected_message_text_view)).check(matches(not(isDisplayed())))
            onView(withId(R.id.get_places_button)).check((matches(not(isDisplayed()))))
            onView(withId(R.id.bottom_round_shape_view)).check((matches(not(isDisplayed()))))
            onView(withId(R.id.not_connected_img)).check((matches(not(isDisplayed()))))
            onView(withId(R.id.map_image_view)).check((matches(not(isDisplayed()))))

        }else{
            /*notConnectedMessage.visibility = View.VISIBLE
            notConnectedImageView.visibility = View.VISIBLE
            mapImage.visibility = View.GONE
            getPlacesButton.text = getString(R.string.try_again)*/
            onView(withId(R.id.not_connected_message_text_view)).check(matches(isDisplayed()))
            onView(withId(R.id.not_connected_img)).check((matches(isDisplayed())))
            onView(withId(R.id.map_image_view)).check((matches(not(isDisplayed()))))
            onView(withId(R.id.get_places_button))
                .check(matches(withText("Try again!")))
        }
    }
}
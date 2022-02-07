package com.ercanpinar.exampleapplication

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Test class showcasing some [RecyclerViewActions] from Espresso.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class UserListAndDetailUITest {
    /**
     * Use [ActivityScenario] to create and launch the activity under test. This is a
     * replacement for [androidx.test.rule.ActivityTestRule].
     */
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test(expected = PerformException::class)
    fun itemWithTextDoesNotExistInUserList() {
        Thread.sleep(1500)
        onView(withId(R.id.userListRecyclerView))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Test User"))
                )
            )
    }

    @Test
    fun selectedUserDetailFlowCheck() {
        Thread.sleep(1500)
        onView(withId(R.id.userListRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Thread.sleep(500)
        onView(withId(R.id.userNameTextView)).check(matches(withText("Ervin Howell")))
        onView(withId(R.id.nameDividerView)).check(matches(isDisplayed()))
        onView(withId(R.id.phoneTitleTextView)).check(matches(withText(R.string.phone)))
        onView(withId(R.id.phoneTextView)).check(matches(withText("010-692-6593 x09125")))
        onView(withId(R.id.emailTitleTextView)).check(matches(withText(R.string.email)))
        onView(withId(R.id.emailTextView)).check(matches(withText("Shanna@melissa.tv")))
        onView(withId(R.id.websiteTitleTextView)).check(matches(withText(R.string.website)))
        onView(withId(R.id.websiteTextView)).check(matches(withText("anastasia.net")))
    }
}
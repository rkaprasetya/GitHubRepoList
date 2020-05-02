package com.raka.repositorieslistgithub.login

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import java.lang.IllegalArgumentException

abstract class ScreenRobot<T:ScreenRobot<T>> {
    private var activityContext : Activity? = null

    fun checkIsDisplayed(@IdRes vararg viewIds:Int):T{
        for(viewId in viewIds){
            Espresso.onView(withId(viewId))
                .check(matches(isDisplayed()))
        }
        return this as T
    }

    fun checkIsHidden(@IdRes vararg viewIds: Int):T{
        for(viewId in viewIds){
            Espresso.onView(withId(viewId))
                .check(matches(not(isDisplayed())))
        }
        return this as T
    }

    fun checkViewHasText(@IdRes viewId:Int,expected:String):T{
        Espresso.onView(withId(viewId)).check(matches(withText(expected)))
        return this as T
    }

    fun checkViewHasText(@IdRes viewId: Int, @StringRes messageResId:Int):T{
        Espresso.onView(withId(viewId)).check(matches(withText(messageResId)))
        return this as T
    }

    fun checkViewHasHint(@IdRes viewId: Int, @StringRes messageResId:Int):T{
        Espresso.onView(withId(viewId)).check(matches(withHint(messageResId)))
        return this as T
    }

    fun clickOnView(@IdRes viewId: Int):T{
        Espresso.onView(withId(viewId))
            .perform(click())
        return this as T
    }

    fun insertTextIntoView(@IdRes viewId: Int, text:String):T{
        Espresso.onView(withId(viewId)).perform(typeText(text), closeSoftKeyboard())
        Thread.sleep(200)
        return this as T
    }

    fun provideActivityContext(activityContext : Activity): T{
        this.activityContext = activityContext
        return this as T
    }

    fun checkDialogueWithTextIsDisplayed(@StringRes messageResId:Int):T{
        Espresso.onView(withText(messageResId))
            .inRoot(withDecorView(not(activityContext!!.window.decorView)))
            .check(matches(isDisplayed()))
        return this as T
    }

    fun swipeLeftOnView(@IdRes viewId: Int):T{
        Espresso.onView(withId(viewId)).perform(swipeLeft())
        return this as T
    }
    companion object{
        fun <T:ScreenRobot<*>> withRobot(screenRobotClass:Class<T>?):T{
            if(screenRobotClass == null){
                throw IllegalArgumentException("instance class is null")
            }

            try {
                return screenRobotClass.newInstance()
            }catch (iae: IllegalAccessException) {
                throw RuntimeException("IllegalAccessException", iae)
            } catch (ie: InstantiationException) {
                throw RuntimeException("InstantiationException", ie)
            }
        }
    }
}
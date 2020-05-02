package com.raka.repositorieslistgithub.login

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.raka.repositorieslistgithub.R
import com.raka.repositorieslistgithub.presentation.login.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginUiTest {
    @JvmField
    @Rule
    var mActivityRule:ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun login_activityLaunchedSuccessfully(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun login_fail_emptyUsername(){
        ScreenRobot.withRobot(LoginScreenRobot::class.java)
            .provideActivityContext(mActivityRule.activity)
            .inputUsernameAndSelectLoginButton("")
            .verifyToastMessageDisplayed(R.string.username_empty)
//        onView(withId(R.id.et_username)).perform(typeText(""))
//        onView(withId(R.id.btn_login)).perform(click())
//        onView(withText(R.string.username_empty)).inRoot(withDecorView(not(mActivityRule.activity.window.decorView))).check(
//            matches(isDisplayed()))
    }
    @Test
    fun login_fail_wrongEmailFormat(){
        ScreenRobot.withRobot(LoginScreenRobot::class.java)
            .provideActivityContext(mActivityRule.activity)
            .inputUsernameAndSelectLoginButton("raka")
            .verifyToastMessageDisplayed(R.string.wrong_email)
//        onView(withId(R.id.et_username)).perform(typeText("rka"), closeSoftKeyboard())
//        Thread.sleep(200)
//        onView(withId(R.id.btn_login)).perform(click())
//        onView(withText(R.string.wrong_email)).inRoot(withDecorView(not(mActivityRule.activity.window.decorView))).check(
//            matches(isDisplayed()))
    }
    @Test
    fun login_fail_emptyPassword(){
        ScreenRobot.withRobot(LoginScreenRobot::class.java)
            .provideActivityContext(mActivityRule.activity)
            .inputUsernameAndPasswordAndClickLoginButton("rka@gmail.com","")
            .verifyToastMessageDisplayed(R.string.password_empty)
//        onView(withId(R.id.et_username)).perform(typeText("rka@gmail.com"), closeSoftKeyboard())
//        onView(withId(R.id.et_password)).perform(typeText(""), closeSoftKeyboard())
//        Thread.sleep(200)
//        onView(withId(R.id.btn_login)).perform(click())
//        onView(withText(R.string.password_empty)).inRoot(withDecorView(not(mActivityRule.activity.window.decorView))).check(
//            matches(isDisplayed()))
    }
    @Test
    fun login_fail_errorServer(){
        ScreenRobot.withRobot(LoginScreenRobot::class.java)
            .provideActivityContext(mActivityRule.activity)
            .inputUsernameAndPasswordAndClickLoginButton("rka@gmail.com","password")
            .verifyToastMessageDisplayed(R.string.login_fail)
//        onView(withId(R.id.et_username)).perform(typeText("rka@gmail.com"), closeSoftKeyboard())
//        onView(withId(R.id.et_password)).perform(typeText("raka"), closeSoftKeyboard())
//        Thread.sleep(200)
//        onView(withId(R.id.btn_login)).perform(click())
//        onView(withText(R.string.login_fail)).inRoot(withDecorView(not(mActivityRule.activity.window.decorView))).check(
//            matches(isDisplayed()))
    }

    @Test
    fun login_fail_progressBarDisplayed(){
        ScreenRobot.withRobot(LoginScreenRobot::class.java)
            .inputUsernameAndPasswordAndClickLoginButton("rka@gmail.com","password")
            .verifyProgressBarDisplayed()
    }
    class LoginScreenRobot:ScreenRobot<LoginScreenRobot>(){
        fun inputUsernameAndSelectLoginButton(text:String):LoginScreenRobot{
            return insertTextIntoView(R.id.et_username,text).clickOnView(R.id.btn_login)
        }

        fun inputUsernameAndPasswordAndClickLoginButton(username:String,password:String):LoginScreenRobot{
            return insertTextIntoView(R.id.et_username,username)
                .insertTextIntoView(R.id.et_password,password)
                .clickOnView(R.id.btn_login)
        }

        fun verifyToastMessageDisplayed(stringId:Int):LoginScreenRobot{
            return checkDialogueWithTextIsDisplayed(stringId)
        }

        fun verifyProgressBarDisplayed():LoginScreenRobot{
            return checkIsDisplayed(R.id.pb_login)
        }

    }
}
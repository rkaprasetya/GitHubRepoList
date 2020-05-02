package com.raka.repositorieslistgithub.presentation.repolist

import android.content.Context
import android.view.View
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.raka.repositorieslistgithub.R
import com.raka.repositorieslistgithub.login.ScreenRobot
import com.raka.repositorieslistgithub.utils.RecyclerViewMatcher
import com.raka.repositorieslistgithub.utils.ViewVisibilityIdlingResource
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.Okio
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoListFragmentTest {
    @JvmField
    @Rule
    var mActivityRule: ActivityTestRule<RepoListActivity> =
        ActivityTestRule(RepoListActivity::class.java)
    private lateinit var mockWebSerVer: MockWebServer
    private var progressBarGone:ViewVisibilityIdlingResource?=null
    @Before
    fun setup() {
        mockWebSerVer = MockWebServer()
        mockWebSerVer.start(8080)
    }

    @After
    fun stopService() {
        mockWebSerVer.shutdown()
        IdlingRegistry.getInstance().unregister(progressBarGone)
    }

    @Test
    fun repoList_recyclerViewIsShowed(){
        //Assert
        mockWebSerVer.setDispatcher(SuccessDispatcher())
        mActivityRule.activity.supportFragmentManager.beginTransaction()
        progressBarGone = ViewVisibilityIdlingResource(mActivityRule.activity.findViewById(R.id.pb_repo_list),
            View.GONE)
        ScreenRobot.withRobot(RepoListScreenRobot::class.java)
            .waitForCondition(progressBarGone)
            .verifyRecyclerViewIsShowed()
    }

    @Test
    fun repoList_progressBarDisplayed() {
        ScreenRobot.withRobot(RepoListScreenRobot::class.java)
            .verifyProgressBarDisplayed()
    }

    class RepoListScreenRobot : ScreenRobot<RepoListScreenRobot>() {
        fun verifyProgressBarDisplayed(): RepoListScreenRobot {
            return checkIsDisplayed(R.id.pb_repo_list)
        }

        fun verifyRecyclerViewIsShowed():RepoListScreenRobot{
            return checkIsDisplayed(R.id.repo_list_rv)
        }
        fun waitForCondition(idlingResource: IdlingResource?) = apply {
            IdlingRegistry.getInstance().register(idlingResource)
        }
    }

    class SuccessDispatcher(private val context: Context = InstrumentationRegistry.getInstrumentation().context):Dispatcher(){


        override fun dispatch(request: RecordedRequest?): MockResponse {
            val errorResponse = MockResponse().setResponseCode(404)
           return MockResponse().setResponseCode(200).setBody(setResponseSuccess("repo_list_response.json"))
//            val pathWithoutQueryParams = Uri.parse(request?.path).path ?: return errorResponse
//            val responseFile = responseFilesByPath[pathWithoutQueryParams]
//
//            return if (responseFile != null) {
//                val responseBody = setResponseSuccess("repo_list_response.json")
//                MockResponse().setResponseCode(200).setBody(responseBody)
//            } else {
//                errorResponse
//            }
        }
        fun setResponseSuccess(fileName: String, headers: Map<String, String> = emptyMap()):String {
            val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
            val source = Okio.buffer(Okio.source(inputStream))
            val mockResponse = MockResponse()
            for ((key, value) in headers) {
                mockResponse.addHeader(key, value)
            }
            return source.readString(Charsets.UTF_8)
//        mockWebSerVer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
        }
    }
}
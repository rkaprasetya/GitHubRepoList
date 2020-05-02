package com.raka.repositorieslistgithub.presentation.login

import com.raka.repositorieslistgithub.RxTrampolineScheluerRule
import com.raka.repositorieslistgithub.data.model.response.DataLoginResponse
import com.raka.repositorieslistgithub.data.model.response.LoginResponse
import com.raka.repositorieslistgithub.network.ApiService
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Tutorial testing rxjava using Single, Observable and flow
 * https://www.ericdecanini.com/2019/09/16/unit-testing-android-with-rxjava-and-retrofit/
 */

@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryImplTest {
    lateinit var SUT: LoginRepositoryImpl
    @Mock
    lateinit var apiService: ApiService
    lateinit var testSub: TestSubscriber<LoginResponse>
    private val PASSWORD = "password123"
    private val EMAIL = "rka.prasetya@gmail.com"

    @Rule
    @JvmField
    val testSchedulerRule = RxTrampolineScheluerRule()

    @Before
    fun setup() {
        SUT = LoginRepositoryImpl(apiService)
        testSub = TestSubscriber()
    }

    @Test
    fun loginRepo_failure_returnEmptyData() {
        //Arrange
        runBlocking {
            `when`(apiService.login2(EMAIL, PASSWORD)).thenReturn(Single.just(getEmptyData()))
            //Act
            val result = SUT.login2(EMAIL, PASSWORD).blockingGet()
            //Assert
            assertThat(result.dataLoginResponse!!.username, `is`(""))

        }
    }

    @Test
    fun loginRepo_success_returnValidData() {
        //Arrange
        runBlocking {
            `when`(apiService.login2(EMAIL, PASSWORD)).thenReturn(Single.just(getValidResponse()))
            //Act
            val result = SUT.login2(EMAIL, PASSWORD).blockingGet()
            //Assert
            assertThat(result.dataLoginResponse!!.username, `is`(EMAIL))
        }
    }

    private fun getValidResponse() = LoginResponse(
        DataLoginResponse(
            "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa",
            "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", EMAIL
        )
    )

    private fun getEmptyData() = LoginResponse(
        DataLoginResponse(
            "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", ""
        )
    )
}
package com.raka.repositorieslistgithub.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.raka.repositorieslistgithub.data.model.response.DataLoginResponse
import com.raka.repositorieslistgithub.data.model.response.LoginResponse
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    lateinit var SUT: LoginViewModel
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @Mock lateinit var observer: Observer<LoginResponse>
    @Mock lateinit var loadingObserver: Observer<Boolean>
    @Mock lateinit var repository: LoginRepository
    @Mock lateinit var toastObserver: Observer<String>
    private val PASSWORD = "password123"
    private val EMAIL = "rka.prasetya@gmail.com"
    @Before
    fun setup() {
        SUT = LoginViewModel(repository)
        SUT.data.observeForever(observer)
        SUT.onLoading.observeForever(loadingObserver)
        SUT.toastMessage.observeForever(toastObserver)
    }

    @Test
    fun onValidationCorrect2_success_returnCorrectData() {
        //Arrange
        val expectedData = getValidResponse()
        //Act
        `when`(repository.login2(EMAIL,PASSWORD)).thenReturn(Single.just(getValidResponse()))
        SUT.onValidationCorrect2(EMAIL,PASSWORD)
        //Assert
        val captor = ArgumentCaptor.forClass(LoginResponse::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertEquals(expectedData, value)
        }
    }

    @Test
    fun onValidationCorrect2_success_dismissLoading() {
        //Arrange
        //Act
        `when`(repository.login2(EMAIL,PASSWORD)).thenReturn(Single.just(getValidResponse()))
        SUT.onValidationCorrect2(EMAIL,PASSWORD)
        //Assert
        val captor = ArgumentCaptor.forClass(Boolean::class.java)
        captor.run {
            verify(loadingObserver, times(1)).onChanged(capture())
            assertEquals(false,value)
        }
    }
    @Test
    fun onValidationCorrect2_success_returnCorrectToastMessage() {
        //Arrange
        //Act
        `when`(repository.login2(EMAIL,PASSWORD)).thenReturn(Single.just(getValidResponse()))
        SUT.onValidationCorrect2(EMAIL,PASSWORD)
        //Assert
        val captor = ArgumentCaptor.forClass(String()::class.java)
        captor.run {
            verify(toastObserver, times(1)).onChanged(capture())
            assertEquals("Login Berhasil",value)
        }
    }

    @Test
    fun onValidationCorrect2_failure_observerNoInteraction() {
        //Arrange
        //Act
        `when`(repository.login2(EMAIL,PASSWORD)).thenReturn(Single.error(Throwable()))
        SUT.onValidationCorrect2(EMAIL,PASSWORD)
        //Assert
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun onValidationCorrect2_failure_returnFailureMessage() {
        //Arrange
        //Act
        `when`(repository.login2(EMAIL,PASSWORD)).thenReturn(Single.error(Throwable()))
        SUT.onValidationCorrect2(EMAIL,PASSWORD)
        //Assert
        val captor = ArgumentCaptor.forClass(String()::class.java)
        captor.run {
            verify(toastObserver, times(1)).onChanged(capture())
            assertEquals("Login Gagal",value)
        }
    }

    private fun getValidResponse() = LoginResponse(
        DataLoginResponse("role","contract","dealerId","dealerName","tokenType","type","userId","responseCode","accessToken",
            "refreshToken","nik","desc","dob","scope","custId","forceChangePass","expiresIbn","email","email")
    )

    private fun getEmptyData() = LoginResponse(
        DataLoginResponse(
            "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", ""
        )
    )
}
package com.raka.repositorieslistgithub.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raka.repositorieslistgithub.data.model.response.LoginResponse
import io.reactivex.subscribers.TestSubscriber
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.*
import org.junit.Assert.*
import org.hamcrest.CoreMatchers.`is`
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class ApiServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var SUT: ApiService
    private lateinit var mockWebSerVer: MockWebServer
    private val PASSWORD = "password123"
    private val EMAIL = "rka.prasetya@gmail.com"
    @Before
    fun createService() {
        mockWebSerVer = MockWebServer()
        mockWebSerVer.start()
        val client = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
        SUT = Retrofit.Builder()
            .baseUrl(mockWebSerVer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebSerVer.shutdown()
    }

    @Test
    fun loginTest_passCorrectPath() {
        runBlocking {
            //Arrange
            setResponseSuccess("response.json")
            //Act
            val resultResponse =  SUT.login(EMAIL,PASSWORD).blockingGet()
            val request = mockWebSerVer.takeRequest()
            //Assert
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/mobile-customer-api/authentication/auth?&email=rka.prasetya%40gmail.com&password=$PASSWORD"))
        }
    }

    @Test
    fun loginTest_success_getCorrectResponse(){
        runBlocking {
            //Arrange
            setResponseSuccess("response.json")
            //Act
            val resultResponse = SUT.login(EMAIL,PASSWORD).blockingGet()
            val dataLoginResponse = resultResponse.dataLoginResponse
            //Response
            assertThat(EMAIL, `is`(dataLoginResponse!!.email))
        }
    }
    @Test
    fun loginTest_success_getTokenNotNull(){
        runBlocking {
            //Arrange
            setResponseSuccess("response.json")
            //Act
            val resultResponse = SUT.login(EMAIL,PASSWORD).blockingGet()
            val dataLoginResponse = resultResponse.dataLoginResponse
            //Response
            assertNotNull(dataLoginResponse?.accessToken)
        }
    }

    @Test
    fun getRepo_success_passCorrectPath(){
        runBlocking {
            //Arrange
            setResponseSuccess("repo_list_response.json")
            //Act
            val resultResponse =  SUT.getRepoRx().blockingGet()
            val request = mockWebSerVer.takeRequest()
            //Assert
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/search/repositories?q=trending&sort=stars"))
        }
    }

    @Test
    fun getRep_success_getCorrectResponse(){
        runBlocking {
            //Arrange
            setResponseSuccess("repo_list_response.json")
            //Act
            val resultResponse = SUT.getRepoRx().blockingGet()
            val dataResponse = resultResponse.items
            //Response
            assertThat("d3", `is`(dataResponse[0].name))
        }
    }

    @Test
    fun getRepo_success_getCorrectDataSize(){
        runBlocking {
            //Arrange
            setResponseSuccess("repo_list_response.json")
            //Act
            val resultResponse = SUT.getRepoRx().blockingGet()
            val dataResponse = resultResponse.items
            //Response
            assertThat(30, `is`(dataResponse.size))
        }
    }

    private fun setResponseSuccess(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebSerVer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
    }

    private fun setResponseFailure(){
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
        mockWebSerVer.enqueue(mockResponse)
    }

}
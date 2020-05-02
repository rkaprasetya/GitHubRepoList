package com.raka.repositorieslistgithub.network

import com.raka.repositorieslistgithub.data.model.response.GitResponse
import com.raka.repositorieslistgithub.data.model.response.LoginResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("mobile-customer-api/authentication/auth?")
    fun login(@Query("email") email: String, @Query("password") password: String): Single<LoginResponse>

    @POST("mobile-customer-api/authentication/auth?")
    fun login2(@Query("email") email: String, @Query("password") password: String): Single<LoginResponse>

    @GET("search/repositories")
    fun getRepoRx(@Query("q") search: String = "trending", @Query("sort") sort: String = "stars"): Single<GitResponse>
}
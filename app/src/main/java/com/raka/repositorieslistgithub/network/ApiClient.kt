package com.raka.repositorieslistgithub.network

import okhttp3.OkHttpClient
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.raka.repositorieslistgithub.util.AppConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
     val apiService : ApiService
     val okHttpClient : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(OkHttpProfilerInterceptor())
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL_GIT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }
}
package com.raka.repositorieslistgithub.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.raka.repositorieslistgithub.network.ApiService
import com.raka.repositorieslistgithub.storage.AppDatabase
import com.raka.repositorieslistgithub.storage.ParametersDao
import com.raka.repositorieslistgithub.util.AppConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule() {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGSON(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL_GIT)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application):ParametersDao{
        val instance : AppDatabase?
        synchronized(AppDatabase::class) {
            instance =  Room.databaseBuilder(
                app.applicationContext,
                AppDatabase::class.java, AppConfig.DB_NAME
            ).build()
        }
        return instance!!.parametersDao()
    }

}
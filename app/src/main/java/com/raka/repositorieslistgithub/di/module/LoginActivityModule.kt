package com.raka.repositorieslistgithub.di.module

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.raka.repositorieslistgithub.network.ApiService
import com.raka.repositorieslistgithub.presentation.login.LoginRepository
import com.raka.repositorieslistgithub.presentation.login.LoginRepositoryImpl
import com.raka.repositorieslistgithub.presentation.login.LoginViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoginActivityModule {
    @Provides
    fun provideLoginActivityViewModel(
        repository: LoginRepository
    ): LoginViewModel = LoginViewModel(repository)

//    @Provides
//    @Singleton
//    fun provideLoginRepository(apiService:ApiService):LoginRepository = LoginRepositoryImpl(apiService)
//
//    @Provides
//    abstract fun provideViewModelFactory(viewModel: LoginViewModel): ViewModelProvider.Factory
}
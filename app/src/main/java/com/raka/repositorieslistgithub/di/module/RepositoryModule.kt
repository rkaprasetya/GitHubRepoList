package com.raka.repositorieslistgithub.di.module

import com.raka.repositorieslistgithub.domain.RepoListRepository
import com.raka.repositorieslistgithub.network.ApiService
import com.raka.repositorieslistgithub.presentation.login.LoginRepository
import com.raka.repositorieslistgithub.presentation.login.LoginRepositoryImpl
import com.raka.repositorieslistgithub.data.repository.RepoListRepositoryImpl
import com.raka.repositorieslistgithub.storage.ParametersDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLoginRepository(apiService:ApiService):LoginRepository = LoginRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideRepoListRepository(dao: ParametersDao, service: ApiService): RepoListRepository =
        RepoListRepositoryImpl(
            dao,
            service
        )
}
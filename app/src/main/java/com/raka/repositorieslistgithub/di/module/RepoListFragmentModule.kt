package com.raka.repositorieslistgithub.di.module

import com.raka.repositorieslistgithub.domain.RepoListRepository
import com.raka.repositorieslistgithub.network.ApiService
import com.raka.repositorieslistgithub.data.repository.RepoListRepositoryImpl
import com.raka.repositorieslistgithub.presentation.repolist.RepoListViewModel
import com.raka.repositorieslistgithub.storage.ParametersDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoListFragmentModule {

    @Provides
    fun provideRepoListViewModel(
        repository: RepoListRepository
    ): RepoListViewModel = RepoListViewModel(repository)

    @Provides
    @Singleton
    fun provideRepoListRepository(dao: ParametersDao,service: ApiService):RepoListRepository =
        RepoListRepositoryImpl(
            dao,
            service
        )
}
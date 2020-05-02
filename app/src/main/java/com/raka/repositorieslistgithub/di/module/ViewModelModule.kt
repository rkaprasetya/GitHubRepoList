package com.raka.repositorieslistgithub.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raka.repositorieslistgithub.di.scope.ViewModelKey
import com.raka.repositorieslistgithub.presentation.login.LoginViewModel
import com.raka.repositorieslistgithub.presentation.repolist.RepoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoListViewModel::class)
    abstract fun bindRepoListViewModel(viewModel: RepoListViewModel):ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory:ViewModelFactory):ViewModelProvider.Factory

//   @Provides
//   fun provideViewModelFactory(loginRepository: LoginRepository,repoListRepository: RepoListRepository, application: Application):ViewModelFactory{
//       return ViewModelFactory()
//   }
}
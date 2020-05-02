package com.raka.repositorieslistgithub.di.module

import com.raka.repositorieslistgithub.presentation.repolist.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector(modules = [RepoListFragmentModule::class])
    abstract fun contributeRepoListFragment():RepoListFragment
}
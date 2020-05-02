package com.raka.repositorieslistgithub.di.module

import com.raka.repositorieslistgithub.presentation.login.MainActivity
import com.raka.repositorieslistgithub.presentation.repolist.RepoListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun contributeMainActivity():MainActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeRepoListActivity():RepoListActivity

}
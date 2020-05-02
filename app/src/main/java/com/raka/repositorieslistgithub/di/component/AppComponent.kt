package com.raka.repositorieslistgithub.di.component

import android.app.Application
import com.raka.repositorieslistgithub.MyApp
import com.raka.repositorieslistgithub.di.module.*
import com.raka.repositorieslistgithub.presentation.login.MainActivity
import com.raka.repositorieslistgithub.presentation.repolist.RepoListActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, ViewModelModule::class,
        RepositoryModule::class,
        ActivityBuilder::class,
        AndroidInjectionModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application:Application):Builder
        fun build():AppComponent
    }
    fun inject(application:MyApp)
}
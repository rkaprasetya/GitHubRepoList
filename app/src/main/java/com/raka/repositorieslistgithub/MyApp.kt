package com.raka.repositorieslistgithub

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.raka.repositorieslistgithub.di.Injectable
import com.raka.repositorieslistgithub.di.component.AppComponent
import com.raka.repositorieslistgithub.di.component.DaggerAppComponent
import com.raka.repositorieslistgithub.di.module.AppModule
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

open class MyApp : Application(), HasActivityInjector {
    lateinit var appComponent: AppComponent
    @Inject
    lateinit var dispatchAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }

    open fun getApiUrl():String{
        return "http://apiurl"
    }

    private fun setupDagger() {
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
        this.registerActivityLifecycleCallbacks(object :Application.ActivityLifecycleCallbacks{
            override fun onActivityPaused(p0: Activity) {

            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityDestroyed(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                    handleActivity(activity)
            }

            override fun onActivityResumed(p0: Activity) {
            }
        })
    }

    override fun activityInjector()=dispatchAndroidInjector
    private fun handleActivity(activity: Activity) {
            AndroidInjection.inject(activity)
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true
                )
        }
    }
}
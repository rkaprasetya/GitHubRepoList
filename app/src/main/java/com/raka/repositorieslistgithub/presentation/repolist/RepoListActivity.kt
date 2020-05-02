package com.raka.repositorieslistgithub.presentation.repolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.phelat.navigationresult.FragmentResultActivity
import com.raka.repositorieslistgithub.MyApp
import com.raka.repositorieslistgithub.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class RepoListActivity : FragmentResultActivity(),HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var bottmNavigationView : BottomNavigationView
    override fun getNavHostFragmentId() = R.id.main_nav_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)
        bottmNavigationView = findViewById(R.id.btm_nav_view)
        NavigationUI.setupWithNavController(bottmNavigationView,findNavController(R.id.main_nav_fragment))
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onSupportNavigateUp() = findNavController(R.id.main_nav_fragment).navigateUp()
}

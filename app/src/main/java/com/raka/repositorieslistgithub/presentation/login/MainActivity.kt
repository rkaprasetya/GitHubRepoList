package com.raka.repositorieslistgithub.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.raka.repositorieslistgithub.MyApp
import com.raka.repositorieslistgithub.R
import com.raka.repositorieslistgithub.base.BaseViewModel
import com.raka.repositorieslistgithub.base.BaseViewModel.State.SUCCESS
import com.raka.repositorieslistgithub.databinding.ActivityMainBinding
import com.raka.repositorieslistgithub.di.module.ViewModelFactory
import com.raka.repositorieslistgithub.presentation.repolist.RepoListActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Link to Navigation component with bottom nav view
 * https://medium.com/@freedom.chuks7/how-to-use-jet-pack-components-bottomnavigationview-with-navigation-ui-19fb120e3fb9
 * Multiple navhost
 * https://proandroiddev.com/mastering-the-bottom-navigation-with-the-new-navigation-architecture-component-cd6a71b266ae
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: LoginViewModel
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProviders.of(this,factory).get(LoginViewModel::class.java)
        binding.viewmodel = viewModel
        setupObservers()
    }


    private fun setupObservers() {
        binding.viewmodel!!.toastMessage.observe(this, Observer {
            it.getContentIfnotHandled()?.let {
                setToastMessage(it)
            }
        })
        binding.viewmodel!!.onLoading.observe(this, Observer {
            if(it){
                binding.pbLogin.visibility = View.VISIBLE
                setFormGone()
            }else{
                binding.pbLogin.visibility = View.GONE
                setFormVisible()
            }
        })
        binding.viewmodel!!.disposable.observe(this, Observer {
            compositeDisposable.add(it)
        })
        binding.viewmodel!!.nextActivity.observe(this, Observer {
            if(it){
                startActivity(Intent(this,RepoListActivity::class.java))
            }
        })
    }

    private fun setToastMessage(it:String){
       var message:String = ""
        when(it!!){
            "1" -> {message = resources.getString(R.string.username_empty)}
            "2" -> {message = resources.getString(R.string.wrong_email)}
            "3" -> {message = resources.getString(R.string.password_empty)}
            "4" -> {message = resources.getString(R.string.login_fail)}
            "5" -> {message = resources.getString(R.string.login_success)}
        }
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun setFormGone(){
        binding.btnLogin.visibility = View.GONE
        binding.etUsername.visibility = View.GONE
        binding.etPassword.visibility = View.GONE
        binding.tvLoginTitle.visibility = View.GONE
    }

    private fun setFormVisible(){
        binding.btnLogin.visibility = View.VISIBLE
        binding.etUsername.visibility = View.VISIBLE
        binding.etPassword.visibility = View.VISIBLE
        binding.tvLoginTitle.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}

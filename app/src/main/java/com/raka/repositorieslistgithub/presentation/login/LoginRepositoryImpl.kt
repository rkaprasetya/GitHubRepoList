package com.raka.repositorieslistgithub.presentation.login

import android.util.Log
import com.raka.repositorieslistgithub.data.model.response.LoginResponse
import com.raka.repositorieslistgithub.network.ApiClient
import com.raka.repositorieslistgithub.network.ApiService
import com.raka.repositorieslistgithub.util.Utils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import javax.inject.Inject

interface LoginRepository{
    fun login(
        username: String,
        password: String,
        onResult: (isSuccess: Boolean, response: LoginResponse?) -> Unit
    )
    fun login2(   username: String,
                  password: String): Single<LoginResponse>
}
class LoginRepositoryImpl @Inject constructor(private val apiService:ApiService):LoginRepository {
    private val compositeDisposable = CompositeDisposable()
    override fun login(
        username: String,
        password: String,
        onResult: (isSuccess: Boolean, response: LoginResponse?) -> Unit
    ) {
        val disposable = apiService.login(username, Utils.hashString("SHA-256", password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    onResult(true, response)
                    Log.e("error","error $response")
                }, {
                    onResult(false, null)
                    Log.e("error","error $it")
                })
    }

    override fun login2(username: String, password: String): Single<LoginResponse> {
        return apiService.login2(username,password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}
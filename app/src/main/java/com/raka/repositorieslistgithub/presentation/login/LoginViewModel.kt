package com.raka.repositorieslistgithub.presentation.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import com.raka.repositorieslistgithub.base.BaseViewModel
import com.raka.repositorieslistgithub.data.model.response.LoginResponse
import com.raka.repositorieslistgithub.util.EventWrapper
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: LoginRepository) : BaseViewModel() {
    var username: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var disposable: MutableLiveData<Disposable> = MutableLiveData()
    var data: MutableLiveData<LoginResponse> = MutableLiveData()
    var nextActivity : MutableLiveData<Boolean> = MutableLiveData()
    fun onLoginClick() {
        if (username.value.isNullOrEmpty()) {
            _toastMessage.value = EventWrapper("1")
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username.value).matches()) {
            _toastMessage.value = EventWrapper("2")
        } else if (password.value.isNullOrEmpty()) {
            _toastMessage.value = EventWrapper("3")
        } else {
            onLoading.value = true
            onValidationCorrect2(username.value.toString().trim(),password.value.toString().trim())
        }
    }

    fun onTextChanged(c:CharSequence,start:Int,before:Int,count:Int){
        Log.e("ontextchanged","ontextchanged seq $c start $start before $before count $count")
    }

    private fun onValidationCorrect() {
        onLoading.value = true
        repository.login(username.value!!, password.value!!) { isSuccess, response ->
            onLoading.value = false
            if (isSuccess) {
                _toastMessage.value = EventWrapper("Login Berhasil")
                Log.e("response", "berhasil = $response")
            } else {
                _toastMessage.value = EventWrapper("Login Gagal")
                Log.e("response", "gagal = $response")
            }
        }
    }
    @VisibleForTesting
     fun onValidationCorrect2(username:String,password:String) {
        val disposable = repository.login2(username, password).subscribe({
            data.value = it
            onLoading.value = false
            nextActivity.value = true
            _toastMessage.value = EventWrapper("5")
            Log.e("response", "berhasil = $it")
        }, {
            onLoading.value = false
            nextActivity.value = true
            _toastMessage.value = EventWrapper("4")
            Log.e("response", "gagal = $it")
        })
        compositeDisposable.add(compositeDisposable)
    }
}
package com.raka.repositorieslistgithub.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raka.repositorieslistgithub.util.EventWrapper
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {
    enum class State {
        SUCCESS,
        FAIL,
        LOADING
    }
    internal var hello = "hello"
    val compositeDisposable = CompositeDisposable()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private var job = Job()
    var onLoading = MutableLiveData<Boolean>().apply { value = false } //NOT USED
    protected val _toastMessage = MutableLiveData<EventWrapper<String>>()
    val toastMessage: LiveData<EventWrapper<String>>
    get() = _toastMessage

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
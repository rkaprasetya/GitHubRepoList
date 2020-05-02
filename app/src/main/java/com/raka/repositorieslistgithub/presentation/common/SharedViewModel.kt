package com.raka.repositorieslistgithub.presentation.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {
    val searchKeywords = MutableLiveData<String>()

    fun share(keywords:String){
        searchKeywords.value = keywords
    }
}
package com.raka.repositorieslistgithub.utils

import com.raka.repositorieslistgithub.MyApp

class TestApplication :MyApp(){
    override fun getApiUrl(): String {
        return "http://127.0.0.1:8080"
    }
}
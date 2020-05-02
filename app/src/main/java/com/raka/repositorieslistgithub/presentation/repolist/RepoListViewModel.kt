package com.raka.repositorieslistgithub.presentation.repolist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.raka.repositorieslistgithub.base.BaseViewModel
import com.raka.repositorieslistgithub.data.model.compact.RepoCompact
import com.raka.repositorieslistgithub.data.model.compact.RepoResponseCompact
import com.raka.repositorieslistgithub.data.model.response.Item
import com.raka.repositorieslistgithub.domain.GetRepoListUseCase
import com.raka.repositorieslistgithub.domain.RepoListRepository
import com.raka.repositorieslistgithub.util.EventWrapper
import com.raka.repositorieslistgithub.util.Utils
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepoListViewModel @Inject constructor(private val repository: RepoListRepository) :
    BaseViewModel() {

    val repoListLiveData = MutableLiveData<List<Item>>() //NOT USED
    var repoCompactData = MutableLiveData<RepoResponseCompact>()
    var disposable: MutableLiveData<Disposable> = MutableLiveData()
    var isSearchClicked: MutableLiveData<Boolean> = MutableLiveData()
    var searchKeyWord: MutableLiveData<String> = MutableLiveData()
    var isLoaded = MutableLiveData<Boolean>().apply { value = false }
    private var completeList: List<RepoCompact> = mutableListOf()
    private val mapper = RepoListMapper()
    val repoListUseCase = GetRepoListUseCase(repository)


    fun loadRepoDataCA() {
        launch {
            val isInternetAvailable = withContext(Dispatchers.IO) {
                Utils.isInternetAvailable()
            }
            if (isInternetAvailable) {
                val disposable = repoListUseCase.getRepoRemoteData().subscribe({
                    repoCompactData.value =
                        RepoResponseCompact(State.SUCCESS, it)
                    _toastMessage.value = EventWrapper("Using Server Data")
                    onLoading.value = false
                    isLoaded.value = true
                    completeList = it
                }, {
                    repoCompactData.value = RepoResponseCompact(State.FAIL, null)
                    onLoading.value = false
                })
                compositeDisposable.add(disposable)
            } else {
                val data = repoListUseCase.loadLocalData()
                if (!data.isNullOrEmpty()) {
                    repoCompactData.value =
                        RepoResponseCompact(State.SUCCESS, data)
                    _toastMessage.value = EventWrapper("Using Local Data")
                    isLoaded.value = true
                    completeList = data
                }else{
                    repoCompactData.value = RepoResponseCompact(State.FAIL, null)
                }
                onLoading.value = false
            }
        }
    }

    fun fetchRepoData() {
        onLoading.value = true
        val disposable = repository.getRepoList()
//            .map { mapper.convertRemoteToRepoCompactData(it.items) }
            .subscribe({ data ->
                mapper.convertRemoteToRepoCompactData(data.items).let {
                    completeList = it
                    repoCompactData.value =
                        RepoResponseCompact(State.SUCCESS, it)
                }
                repository.insertRepoListToDb(mapper.convertRemoteToLocal(data.items))
                _toastMessage.value = EventWrapper("Using Server Data")
                onLoading.value = false
            }, {
                repoCompactData.value = RepoResponseCompact(State.FAIL, null)
                onLoading.value = false
            })
        compositeDisposable.add(disposable)
    }

    fun search() {
        if (searchKeyWord.value.toString() == "") {
            loadRepoList()
        } else {
            val newList: MutableList<RepoCompact> = mutableListOf()
            for (item in completeList) {
                if (item.description.toLowerCase().contains(searchKeyWord.value.toString())) {
                    newList.add(item)
                }
            }
            repoCompactData.value = RepoResponseCompact(State.SUCCESS, newList)
        }
    }

    fun loadRepoList() {
        launch {
            val isInternetAvailable = withContext(Dispatchers.IO) {
                Utils.isInternetAvailable()
            }
            if (isInternetAvailable) {
//                fetchRepoRemote()
                fetchRepoData()
                isLoaded.value = true
            } else {
                fetchRepoLocal()
                isLoaded.value = true
            }
        }
    }

    private fun fetchRepoLocal() {
        repoCompactData.value = RepoResponseCompact(State.LOADING, null)
        launch {
            val dataLocal = repository.getRepoListLocal().map { mapper.mapLocalToRepoCompact(it) }
            if (dataLocal.isNullOrEmpty()) {
                repoCompactData.value = RepoResponseCompact(State.FAIL, null)
            } else {
                repoCompactData.value = RepoResponseCompact(State.SUCCESS, dataLocal)
                completeList = dataLocal
                _toastMessage.value = EventWrapper("Using Local Data")
            }
        }
    }

    fun fetchRepoRemote() {
        repoCompactData.value = RepoResponseCompact(State.LOADING, null)
        repository.getRepoRemote { isSuccess, response ->
            if (isSuccess) {
                mapper.convertRemoteToRepoCompactData(response!!).let {
                    completeList = it
                    repoCompactData.value =
                        RepoResponseCompact(State.SUCCESS, it)
                }
                repository.insertRepoListToDb(mapper.convertRemoteToLocal(response))
                _toastMessage.value = EventWrapper("Using Server Data")
            } else {
                repoCompactData.value = RepoResponseCompact(State.FAIL, null)
            }
            onLoading.value = false
        }
    }

    fun getRemoteData() {  //NOT USED
        onLoading.value = true
        repository.getRepoCompact { isSuccess, response ->
            Log.e("success", "isSuccess = ${isSuccess}")
            if (isSuccess) {
                repoCompactData.value = RepoResponseCompact(State.SUCCESS, response)
            } else {
                repoCompactData.value = RepoResponseCompact(State.FAIL, null)
            }
            onLoading.value = false
        }
    }

}

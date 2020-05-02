package com.raka.repositorieslistgithub.data.repository

import android.annotation.SuppressLint
import com.raka.repositorieslistgithub.data.model.compact.RepoCompact
import com.raka.repositorieslistgithub.data.model.local.RepoListLocal
import com.raka.repositorieslistgithub.data.model.response.GitResponse
import com.raka.repositorieslistgithub.data.model.response.Item
import com.raka.repositorieslistgithub.domain.RepoListRepository
import com.raka.repositorieslistgithub.network.ApiClient
import com.raka.repositorieslistgithub.network.ApiService
import com.raka.repositorieslistgithub.presentation.repolist.RepoListMapper
import com.raka.repositorieslistgithub.storage.ParametersDao
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
//interface RepoListRepository {
//    fun getRepoList(): Single<GitResponse>
//    fun getRepoCompact(onResult: (isSuccess: Boolean, response: List<RepoCompact>?) -> Unit)
//    fun getRepoRemote(onResult: (isSuccess: Boolean, response: List<Item>?) -> Unit)
//    fun insertRepoListToDb(data:List<RepoListLocal>)
//    suspend fun getRepoListLocal():List<RepoListLocal>
//}
class RepoListRepositoryImpl (private val dao:ParametersDao, private val service:ApiService):
    RepoListRepository,CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
    private var job = Job()
    val mapper = RepoListMapper()
    @SuppressLint("CheckResult")
    override fun getRepoList():Single<GitResponse>{
        val response =  ApiClient().apiService.getRepoRx().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        response.doAfterSuccess { insertRepoListToDb(mapper.convertRemoteToLocal(it.items)) }
        return response
    }

    override fun getRepoCompact(onResult: (isSuccess: Boolean, response: List<RepoCompact>?) -> Unit) {
        val disposable = ApiClient().apiService.getRepoRx().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                onResult(true,setRepoCompactData(result.items))
            },{
                onResult(false,null)
            })
    }

    override fun getRepoRemote(onResult: (isSuccess: Boolean, response: List<Item>?) -> Unit) {
        val disposable = ApiClient().apiService.getRepoRx().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                onResult(true,result.items)
            },{
                onResult(false,null)
            })
    }

    override fun insertRepoListToDb(data: List<RepoListLocal>) {
        launch {
            dao.deleteRepoList()
            dao.insertRepoList(data)
        }
    }

    override suspend fun getRepoListLocal(): List<RepoListLocal> {
        return withContext(Dispatchers.IO) {
            dao.getRepoList()
        }
    }

    private fun setRepoCompactData(item: List<Item>):List<RepoCompact> {
        val dataCompact: MutableList<RepoCompact> = mutableListOf()
        item.forEach { data ->
            RepoCompact(
                data.full_name,
                data.description,
                data.forks_count,
                data.stargazers_count,
                data.open_issues_count,
                data.owner.avatar_url,
                data.owner.html_url
            ).let { dataCompact.add(it) }
        }
        return dataCompact
    }

//    companion object {
//        private var INSTANCE: RepoListRepository? = null
//        fun getInstance( context: Context) = INSTANCE ?: RepoListRepositoryImpl(context).also {
//            INSTANCE = it
//        }
//    }
}
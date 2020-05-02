package com.raka.repositorieslistgithub.domain

import com.raka.repositorieslistgithub.data.model.compact.RepoCompact
import com.raka.repositorieslistgithub.data.model.local.RepoListLocal
import com.raka.repositorieslistgithub.data.model.response.GitResponse
import com.raka.repositorieslistgithub.data.model.response.Item
import io.reactivex.Single

interface RepoListRepository {
    fun getRepoList(): Single<GitResponse>
    fun getRepoCompact(onResult: (isSuccess: Boolean, response: List<RepoCompact>?) -> Unit)
    fun getRepoRemote(onResult: (isSuccess: Boolean, response: List<Item>?) -> Unit)
    fun insertRepoListToDb(data:List<RepoListLocal>)
    suspend fun getRepoListLocal():List<RepoListLocal>
}
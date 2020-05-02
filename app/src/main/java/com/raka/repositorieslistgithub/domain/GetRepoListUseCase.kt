package com.raka.repositorieslistgithub.domain

import com.raka.repositorieslistgithub.data.model.compact.RepoCompact
import com.raka.repositorieslistgithub.presentation.repolist.RepoListMapper
import io.reactivex.Single
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class GetRepoListUseCase(private val repoListRepository: RepoListRepository) {

    val mapper = RepoListMapper()
    fun getRepoRemoteData():Single<List<RepoCompact>> = loadRemoteData()

    private fun loadRemoteData() = repoListRepository.getRepoList().map { mapper.convertRemoteToRepoCompactData(it.items) }
    suspend fun loadLocalData() = repoListRepository.getRepoListLocal().map { mapper.mapLocalToRepoCompact(it) }
}
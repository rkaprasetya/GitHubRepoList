package com.raka.repositorieslistgithub.presentation.repolist

import com.raka.repositorieslistgithub.data.model.compact.RepoCompact
import com.raka.repositorieslistgithub.data.model.local.RepoListLocal
import com.raka.repositorieslistgithub.data.model.response.Item

class RepoListMapper {
    fun convertRemoteToRepoCompactData(item: List<Item>): List<RepoCompact> {
        val dataCompact: MutableList<RepoCompact> = mutableListOf()
        item.forEach { data ->
            mapItemToRepoCompact(data).let { dataCompact.add(it) }
        }
        return dataCompact
    }

    fun mapItemToRepoCompact(data:Item) = RepoCompact(data.full_name,
        data.description,
        data.forks_count,
        data.stargazers_count,
        data.open_issues_count,
        data.owner.avatar_url,
        data.owner.html_url)

    fun convertLocalToRepoCompactData(item: List<RepoListLocal>): List<RepoCompact> {
        val dataCompact: MutableList<RepoCompact> = mutableListOf()
        item.forEach { data ->
            mapLocalToRepoCompact(data).let { dataCompact.add(it) }
        }
        return dataCompact
    }

    fun mapLocalToRepoCompact(data:RepoListLocal) = RepoCompact(data.full_name,
        data.description,
        data.forks_count,
        data.stargazers_count,
        data.open_issues_count,
        data.avatar_url,
        data.html_url)

    fun convertRemoteToLocal(item: List<Item>): List<RepoListLocal> {
        val dataCompact: MutableList<RepoListLocal> = mutableListOf()
        item.forEach { data ->
            mapRemoteToLocal(data).let { dataCompact.add(it) }
        }
        return dataCompact
    }

    private fun mapRemoteToLocal(data:Item)=RepoListLocal(
        0,
        data.full_name,
        data.description,
        data.forks_count,
        data.stargazers_count,
        data.open_issues_count,
        data.owner.avatar_url,
        data.owner.html_url
    )
}
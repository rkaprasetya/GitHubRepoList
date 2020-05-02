package com.raka.repositorieslistgithub.data.model.compact

class RepoCompact(
    val full_name: String,
    val description: String,
    val forks_count: Int,
    val stargazers_count: Int,
    val open_issues_count: Int,
    val avatar_url: String,
    val html_url: String
)
package com.raka.repositorieslistgithub.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "repolist")
data class RepoListLocal(
    @ColumnInfo(name = "roomId")
    @PrimaryKey(autoGenerate = true)
    val roomId: Long = 0,
    @ColumnInfo(name = "full_name")
    val full_name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "forks_count")
    val forks_count: Int,
    @ColumnInfo(name = "stargazers_count")
    val stargazers_count: Int,
    @ColumnInfo(name = "open_issues_count")
    val open_issues_count: Int,
    @ColumnInfo(name = "avatar_url")
    val avatar_url: String,
    @ColumnInfo(name = "html_url")
    val html_url: String
)
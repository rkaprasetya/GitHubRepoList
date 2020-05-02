package com.raka.repositorieslistgithub.data.model.compact

import com.raka.repositorieslistgithub.base.BaseViewModel

data class RepoResponseCompact(
    val state: BaseViewModel.State,
    val data: List<RepoCompact>?
)
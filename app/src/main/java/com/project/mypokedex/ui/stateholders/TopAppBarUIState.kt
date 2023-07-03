package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.TopAppBarActionItem

data class TopAppBarUIState(
    val title: String = "MyPokedex",
    val hasReturn: Boolean = false,
    val onClickReturn: () -> Unit = {},
    val actionItems: List<TopAppBarActionItem> = emptyList()
)

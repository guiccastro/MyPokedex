package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.TopAppBarItem

data class TopAppBarUIState(
    val itemsList: List<TopAppBarItem> = emptyList(),
    val title: String? = null
)

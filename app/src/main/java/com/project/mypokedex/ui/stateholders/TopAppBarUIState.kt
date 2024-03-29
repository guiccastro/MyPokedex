package com.project.mypokedex.ui.stateholders

import androidx.annotation.StringRes
import com.project.mypokedex.R
import com.project.mypokedex.model.TopAppBarActionItem

data class TopAppBarUIState(
    @StringRes val title: Int = R.string.app_name,
    val hasMenu: Boolean = false,
    val hasReturn: Boolean = false,
    val onClickReturn: () -> Unit = {},
    val actionItems: List<TopAppBarActionItem> = emptyList()
)

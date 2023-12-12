package com.project.mypokedex.ui.stateholders

import androidx.annotation.StringRes
import com.project.mypokedex.model.downloadInfo.DownloadType

data class AnimatedEnterUIState(
    val openApp: Boolean = false,
    val showDownloadMessage: Boolean = false,
    val downloadInfoType: DownloadType = DownloadType.None,
    @StringRes val downloadNewProperties: List<Int> = emptyList(),
    val onDownloadClick: () -> Unit = {},
)

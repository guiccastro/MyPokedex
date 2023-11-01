package com.project.mypokedex.ui.stateholders

import androidx.annotation.StringRes
import com.project.mypokedex.model.downloadInfo.DownloadType

data class AnimatedEnterUIState(
    val isDownloading: Boolean = true,
    val downloadProgress: Float = 0F,
    val formattedDownloadProgress: String = "",
    val showDownloadMessage: Boolean = false,
    val downloadInfoType: DownloadType = DownloadType.None,
    @StringRes val downloadNewProperties: List<Int> = emptyList(),
    val onDownloadClick: () -> Unit = {},
)

package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.downloadInfo.DownloadInfoType

data class AnimatedEnterUIState(
    val isDownloading: Boolean = true,
    val downloadProgress: Float = 0F,
    val formattedDownloadProgress: String = "",
    val showDownloadMessage: Boolean = false,
    val downloadInfoType: DownloadInfoType = DownloadInfoType.None(),
    val onDownloadClick: () -> Unit = {},
)

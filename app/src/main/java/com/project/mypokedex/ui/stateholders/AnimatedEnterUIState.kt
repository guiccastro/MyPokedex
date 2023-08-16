package com.project.mypokedex.ui.stateholders

data class AnimatedEnterUIState(
    val isDownloading: Boolean = true,
    val downloadProgress: Float = 0F,
    val formattedDownloadProgress: String = "",
    val showDownloadMessage: Boolean = false,
    val onDownloadClick: () -> Unit = {},
)

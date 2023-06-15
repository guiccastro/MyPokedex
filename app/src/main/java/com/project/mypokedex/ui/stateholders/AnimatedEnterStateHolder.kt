package com.project.mypokedex.ui.stateholders

data class AnimatedEnterStateHolder(
    val isDownloading: Boolean = true,
    val downloadProgress: Float = 0F,
    val formattedDownloadProgress: String = "",
)

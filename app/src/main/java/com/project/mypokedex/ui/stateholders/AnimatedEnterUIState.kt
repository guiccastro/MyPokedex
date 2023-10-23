package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.PokemonDownloadInfo

data class AnimatedEnterUIState(
    val isDownloading: Boolean = true,
    val downloadProgress: Float = 0F,
    val formattedDownloadProgress: String = "",
    val showDownloadMessage: Boolean = false,
    val downloadInfoType: PokemonDownloadInfo = PokemonDownloadInfo.None,
    val onDownloadClick: () -> Unit = {},
)

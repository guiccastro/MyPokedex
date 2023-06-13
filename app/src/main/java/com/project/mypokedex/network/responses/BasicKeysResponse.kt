package com.project.mypokedex.network.responses

data class BasicKeysResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<KeyResponse>
)

data class KeyResponse(
    val name: String,
    val url: String
)

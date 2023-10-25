package com.project.mypokedex.model.downloadInfo

interface RequestUpdateClass {

    suspend fun requestInfo(id: Int)
}
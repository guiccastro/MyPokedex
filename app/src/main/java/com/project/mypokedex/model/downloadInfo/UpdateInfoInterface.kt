package com.project.mypokedex.model.downloadInfo

import androidx.annotation.StringRes
import com.project.mypokedex.model.Pokemon

interface UpdateInfoInterface {

    val updateClass: UpdateClass

    fun needToRequest(pokemon: Pokemon): Boolean

    @StringRes
    fun getDescription(): Int
}
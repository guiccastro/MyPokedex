package com.project.mypokedex.data

import com.google.gson.annotations.SerializedName


data class GenerationV (

  @SerializedName("black-white" ) var black_white : BlackWhite? = BlackWhite()

)
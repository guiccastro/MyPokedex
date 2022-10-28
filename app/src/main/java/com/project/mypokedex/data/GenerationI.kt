package com.project.mypokedex.data

import com.google.gson.annotations.SerializedName


data class GenerationI (

    @SerializedName("red-blue" ) var red_blue : RedBlue? = RedBlue(),
    @SerializedName("yellow"   ) var yellow   : Yellow?   = Yellow()

)
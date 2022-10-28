package com.project.mypokedex.data

import com.google.gson.annotations.SerializedName


data class GenerationVII (

    @SerializedName("icons"                ) var icons                : Icons?                = Icons(),
    @SerializedName("ultra-sun-ultra-moon" ) var ultra_sun_ultra_moon : UltraSunUltraMoon? = UltraSunUltraMoon()

)
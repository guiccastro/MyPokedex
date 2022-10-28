package com.project.mypokedex.data

import com.google.gson.annotations.SerializedName


data class Other (

    @SerializedName("dream_world"      ) var dreamWorld       : DreamWorld?       = DreamWorld(),
    @SerializedName("home"             ) var home             : Home?             = Home(),
    @SerializedName("official-artwork" ) var official_artwork : OfficialArtwork? = OfficialArtwork()

)
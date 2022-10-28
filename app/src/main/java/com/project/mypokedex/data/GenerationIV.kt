package com.project.mypokedex.data

import com.google.gson.annotations.SerializedName


data class GenerationIV (

    @SerializedName("diamond-pearl"        ) var diamond_pearl        : DiamondPearl?        = DiamondPearl(),
    @SerializedName("heartgold-soulsilver" ) var heartgold_soulsilver : HeartgoldSoulsilver? = HeartgoldSoulsilver(),
    @SerializedName("platinum"             ) var platinum             : Platinum?             = Platinum()

)
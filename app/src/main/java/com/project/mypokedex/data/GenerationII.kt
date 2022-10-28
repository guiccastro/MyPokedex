package com.project.mypokedex.data

import com.google.gson.annotations.SerializedName


data class GenerationII (

    @SerializedName("crystal" ) var crystal : Crystal? = Crystal(),
    @SerializedName("gold"    ) var gold    : Gold?    = Gold(),
    @SerializedName("silver"  ) var silver  : Silver?  = Silver()

)
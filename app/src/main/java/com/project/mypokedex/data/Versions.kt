package com.project.mypokedex.data

import com.google.gson.annotations.SerializedName
import com.project.mypokedex.data.*


data class Versions (

    @SerializedName("generation-i"    ) var generation_i    : GenerationI?    = GenerationI(),
    @SerializedName("generation-ii"   ) var generation_ii   : GenerationII?   = GenerationII(),
    @SerializedName("generation-iii"  ) var generation_iii  : GenerationIII?  = GenerationIII(),
    @SerializedName("generation-iv"   ) var generation_iv   : GenerationIV?   = GenerationIV(),
    @SerializedName("generation-v"    ) var generation_v    : GenerationV?    = GenerationV(),
    @SerializedName("generation-vi"   ) var generation_vi   : GenerationVI?   = GenerationVI(),
    @SerializedName("generation-vii"  ) var generation_vii  : GenerationVII?  = GenerationVII(),
    @SerializedName("generation-viii" ) var generation_viii : GenerationVIII? = GenerationVIII()

)
package com.project.mypokedex.data

import com.google.gson.annotations.SerializedName


data class GenerationVI (

    @SerializedName("omegaruby-alphasapphire" ) var omegaruby_alphasapphire : OmegarubyAlphasapphire? = OmegarubyAlphasapphire(),
    @SerializedName("x-y"                     ) var x_y                     : XY?                     = XY()

)
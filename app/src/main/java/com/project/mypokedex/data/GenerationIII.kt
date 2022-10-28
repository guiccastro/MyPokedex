package com.project.mypokedex.data

import com.google.gson.annotations.SerializedName


data class GenerationIII (

    @SerializedName("emerald"           ) var emerald           : Emerald?           = Emerald(),
    @SerializedName("firered-leafgreen" ) var firered_leafgreen : FireredLeafgreen? = FireredLeafgreen(),
    @SerializedName("ruby-sapphire"     ) var ruby_sapphire     : RubySapphire?     = RubySapphire()

)
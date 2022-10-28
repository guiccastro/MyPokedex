package com.project.mypokedex.data

import com.google.gson.annotations.SerializedName


data class Yellow (

  @SerializedName("back_default"  ) var backDefault  : String? = null,
  @SerializedName("back_gray"     ) var backGray     : String? = null,
  @SerializedName("front_default" ) var frontDefault : String? = null,
  @SerializedName("front_gray"    ) var frontGray    : String? = null

)
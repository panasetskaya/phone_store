package com.panasetskaia.core.domain.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BestSeller(
    val id: String,
    val isNew: Boolean? = false,
    val title: String? = null,
    val pic_url: String? = null,
    val noDiscountPrice: Int? = null,
    val discountPrice: Int? = null,
)

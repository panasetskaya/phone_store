package com.panasetskaia.core.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BestSellerDtoModel (
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("is_favorites")
    @Expose
    val isFav: Boolean? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("picture")
    @Expose
    val picUrl: String? = null,

    @SerializedName("price_without_discount")
    @Expose
    val noDiscountPrice: Int? = null,

    @SerializedName("discount_price")
    @Expose
    val discountPrice: Int? = null,
        )
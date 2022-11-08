package com.panasetskaia.core.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BestSellerDataModel (
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("is_favourites")
    @Expose
    val isNew: Boolean? = false,

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
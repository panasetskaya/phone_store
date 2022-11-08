package com.panasetskaia.core.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HotSaleDataModel (
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("is_new")
    @Expose
    val isNew: Boolean? = false,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("subtitle")
    @Expose
    val subtitle: String? = null,

    @SerializedName("picture")
    @Expose
    val picUrl: String? = null,

    @SerializedName("is_buy")
    @Expose
    val isBuy: Boolean? = false
)
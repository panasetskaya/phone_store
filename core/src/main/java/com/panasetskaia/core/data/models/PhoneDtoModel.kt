package com.panasetskaia.core.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PhoneDtoModel(
    @SerializedName("id")
    @Expose
    val id: String? = null,

    @SerializedName("isFavorites")
    @Expose
    val isFavorite: Boolean? = false,

    @SerializedName("CPU")
    @Expose
    val CPU: String? = null,

    @SerializedName("camera")
    @Expose
    val camera: String? = null,

    @SerializedName("capacity")
    @Expose
    val capacities: ArrayList<String>? = null,

    @SerializedName("color")
    @Expose
    val colors: ArrayList<String>? = null,

    @SerializedName("images")
    @Expose
    val images: ArrayList<String>? = null,

    @SerializedName("price")
    @Expose
    val price: Int? = null,

    @SerializedName("rating")
    @Expose
    val rating: Float? = null,

    @SerializedName("sd")
    @Expose
    val sd: String? = null,

    @SerializedName("ssd")
    @Expose
    val ssd: String? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null
)
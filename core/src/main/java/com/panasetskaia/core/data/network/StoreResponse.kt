package com.panasetskaia.core.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.panasetskaia.core.data.models.BestSellerDataModel
import com.panasetskaia.core.data.models.HotSaleDataModel

data class StoreResponse(
    @SerializedName("home_store")
    @Expose
    val hotSales: ArrayList<HotSaleDataModel>? = null,

    @SerializedName("best_seller")
    @Expose
    val bestSellers: ArrayList<BestSellerDataModel>? = null
)

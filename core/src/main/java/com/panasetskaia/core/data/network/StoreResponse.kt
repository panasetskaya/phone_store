package com.panasetskaia.core.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.panasetskaia.core.data.models.BestSellerDtoModel
import com.panasetskaia.core.data.models.HotSaleDtoModel

data class StoreResponse(
    @SerializedName("home_store")
    @Expose
    val hotSales: ArrayList<HotSaleDtoModel>,

    @SerializedName("best_seller")
    @Expose
    val bestSellers: ArrayList<BestSellerDtoModel>
)

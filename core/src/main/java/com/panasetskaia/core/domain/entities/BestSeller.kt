package com.panasetskaia.core.domain.entities

data class BestSeller(
    val id: String,
    val isFav: Boolean = false,
    val title: String? = null,
    val picUrl: String? = null,
    val noDiscountPrice: Int? = null,
    val discountPrice: Int? = null,
)

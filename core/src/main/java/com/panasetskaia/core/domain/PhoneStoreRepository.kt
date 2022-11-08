package com.panasetskaia.core.domain

import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.Phone

interface PhoneStoreRepository {

    suspend fun getSinglePhone(): Phone

    suspend fun getHotSales(): List<HotSale>

    suspend fun getBestSellers(): List<BestSeller>

    suspend fun addToCart(phone: Phone)

    suspend fun deleteFromCart(phone: Phone)

    suspend fun getCart(): List<Phone>

}
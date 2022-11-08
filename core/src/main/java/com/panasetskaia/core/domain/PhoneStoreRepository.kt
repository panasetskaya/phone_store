package com.panasetskaia.core.domain

import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.Phone
import kotlinx.coroutines.flow.Flow

interface PhoneStoreRepository {

    suspend fun getSinglePhone(): Flow<Phone>

    suspend fun getHotSales(): Flow<List<HotSale>>

    suspend fun getBestSellers(): Flow<List<BestSeller>>

    suspend fun addToCart(phone: Phone)

    suspend fun deleteFromCart(phone: Phone)

    suspend fun getCart(): Flow<List<Phone>>

}
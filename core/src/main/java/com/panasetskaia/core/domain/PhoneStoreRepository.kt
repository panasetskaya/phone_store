package com.panasetskaia.core.domain

import com.panasetskaia.core.domain.entities.NetworkResult
import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.Phone
import kotlinx.coroutines.flow.Flow

interface PhoneStoreRepository {

    suspend fun getSinglePhone(): Flow<NetworkResult<Phone>>

    suspend fun getHotSales(): Flow<NetworkResult<List<HotSale>>>

    suspend fun getBestSellers(): Flow<NetworkResult<List<BestSeller>>>

    suspend fun addToCart(phone: Phone)

    suspend fun deleteFromCart(phone: Phone)

    suspend fun getCart(): Flow<List<Phone>>

}
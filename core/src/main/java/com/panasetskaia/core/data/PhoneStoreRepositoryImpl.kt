package com.panasetskaia.core.data

import com.panasetskaia.core.data.network.ApiFactory
import com.panasetskaia.core.data.network.ApiService
import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.core.domain.PhoneStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PhoneStoreRepositoryImpl: PhoneStoreRepository {

    private val apiService = ApiFactory.apiService
    private val mapper = PhoneMapper()

    override suspend fun getSinglePhone(): Flow<Phone> = flow {
        val phoneDto = apiService.getSinglePhone()
        emit(mapper.mapPhoneDtoModelToEntity(phoneDto))
    }

    override suspend fun getHotSales(): Flow<List<HotSale>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBestSellers(): Flow<List<BestSeller>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToCart(phone: Phone) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromCart(phone: Phone) {
        TODO("Not yet implemented")
    }

    override suspend fun getCart(): Flow<List<Phone>> {
        TODO("Not yet implemented")
    }

}
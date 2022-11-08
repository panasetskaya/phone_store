package com.panasetskaia.core.data

import com.panasetskaia.core.data.network.ApiFactory
import com.panasetskaia.core.domain.PhoneStoreRepository
import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.Phone
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
        val hotSalesDto = apiService.getStore().hotSales
        val hotSales = mutableListOf<HotSale>()
        for (i in hotSalesDto) {
            hotSales.add(mapper.mapHotSaleDataModelToEntity(i))
        }
        return flow {
            emit(hotSales)
        }
    }

    override suspend fun getBestSellers(): Flow<List<BestSeller>> {
        val bestSellersDto = apiService.getStore().bestSellers
        val bestSellers = mutableListOf<BestSeller>()
        for (i in bestSellersDto) {
            bestSellers.add(mapper.mapBestSellerDataModelToEntity(i))
        }
        return flow {
            emit(bestSellers)
        }
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
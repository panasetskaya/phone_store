package com.panasetskaia.core.data

import android.app.Application
import com.panasetskaia.core.data.database.CartDatabase
import com.panasetskaia.core.data.network.ApiFactory
import com.panasetskaia.core.domain.PhoneStoreRepository
import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.NetworkResult
import com.panasetskaia.core.domain.entities.Phone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PhoneStoreRepositoryImpl(application: Application): PhoneStoreRepository {

    private val apiService = ApiFactory.apiService
    private val mapper = PhoneMapper()
    private val db = CartDatabase.getInstance(application)

    override suspend fun getSinglePhone(): Flow<NetworkResult<Phone>> = flow {
        val phone = mapper.mapPhoneDtoModelToEntity (apiService.getSinglePhone())
            emit(NetworkResult.success(phone))
        }.flowOn(Dispatchers.IO)


    override suspend fun getHotSales(): Flow<NetworkResult<List<HotSale>>> {
        val hotSalesDto = apiService.getStore().hotSales
        val hotSales = mutableListOf<HotSale>()
        for (i in hotSalesDto) {
            hotSales.add(mapper.mapHotSaleDataModelToEntity(i))
        }
        return flow {
            emit(NetworkResult.success(hotSales))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getBestSellers(): Flow<NetworkResult<List<BestSeller>>> {
        val bestSellersDto = apiService.getStore().bestSellers
        val bestSellers = mutableListOf<BestSeller>()
        for (i in bestSellersDto) {
            bestSellers.add(mapper.mapBestSellerDataModelToEntity(i))
        }
        return flow {
            emit(NetworkResult.success(bestSellers))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun addToCart(phone: Phone) {
        val dbModel = mapper.mapPhoneToDbModel(phone)
        db.cartDao().addToCart(dbModel)
    }

    override suspend fun deleteFromCart(phone: Phone) {
        val dbModel = mapper.mapPhoneToDbModel(phone)
        db.cartDao().deleteFromCart(dbModel.id)
    }

    override suspend fun getCart(): Flow<List<Phone>> {
        return db.cartDao().getAllCart().map {
            val list = mutableListOf<Phone>()
            for (i in it) {
                list.add(mapper.mapDbModelToPhone(i))
            }
            list
        }
    }

}
package com.panasetskaia.core.data

import com.panasetskaia.core.data.database.CartDao
import com.panasetskaia.core.data.network.ApiService
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
import javax.inject.Inject

class PhoneStoreRepositoryImpl @Inject constructor(
    private val dao: CartDao,
    private val mapper: PhoneMapper,
    private val apiService: ApiService
) : PhoneStoreRepository {

    override suspend fun getSinglePhone(): Flow<NetworkResult<Phone>> {
        return try {
            val phone = mapper.mapPhoneDtoModelToEntity(apiService.getSinglePhone())
            flow { emit(NetworkResult.success(phone)) }.flowOn(Dispatchers.IO)
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.error(e.message ?: "Error while loading"))
            }
        }
    }

    override suspend fun getHotSales(): Flow<NetworkResult<List<HotSale>>> {
        return try {
            val hotSalesDto = apiService.getStore().hotSales
            val hotSales = hotSalesDto.map {
                mapper.mapHotSaleDataModelToEntity(it)
            }
            flow {
                emit(NetworkResult.success(hotSales))
            }.flowOn(Dispatchers.IO)
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.error(e.message ?: "Error while loading"))
            }
        }
    }

    override suspend fun getBestSellers(): Flow<NetworkResult<List<BestSeller>>> {
        return try {
            val bestSellersDto = apiService.getStore().bestSellers
            val bestSellers = bestSellersDto.map {
                mapper.mapBestSellerDataModelToEntity(it)
            }
            flow {
                emit(NetworkResult.success(bestSellers))
            }.flowOn(Dispatchers.IO)
        } catch (e: Exception) {
            flow {
                emit(NetworkResult.error(e.message ?: "Error while loading"))
            }
        }
    }

    override suspend fun addToCart(phone: Phone) {
        val dbModel = mapper.mapPhoneToDbModel(phone)
        dao.addToCart(dbModel)
    }

    override suspend fun deleteFromCart(phone: Phone) {
        val dbModel = mapper.mapPhoneToDbModel(phone)
        dao.deleteFromCart(dbModel.id)
    }

    override suspend fun getCart(): Flow<List<Phone>> {
        return dao.getAllCart().map { dbList ->
            dbList.map { dbModel ->
                mapper.mapDbModelToPhone(dbModel)
            }
        }
    }

    override suspend fun getCartSize(): Flow<Int> {
        return dao.getCartSize()
    }
}
package com.panasetskaia.core.data

import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.core.domain.PhoneStoreRepository

class PhoneStoreRepositoryImpl: PhoneStoreRepository {
    override suspend fun getSinglePhone(): Phone {
        TODO("Not yet implemented")
    }

    override suspend fun getHotSales(): List<HotSale> {
        TODO("Not yet implemented")
    }

    override suspend fun getBestSellers(): List<BestSeller> {
        TODO("Not yet implemented")
    }

    override suspend fun addToCart(phone: Phone) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromCart(phone: Phone) {
        TODO("Not yet implemented")
    }

    override suspend fun getCart(): List<Phone> {
        TODO("Not yet implemented")
    }
}
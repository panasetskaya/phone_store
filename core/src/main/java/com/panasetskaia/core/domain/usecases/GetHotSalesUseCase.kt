package com.panasetskaia.core.domain.usecases

import com.panasetskaia.core.domain.PhoneStoreRepository
import com.panasetskaia.core.domain.entities.HotSale
import kotlinx.coroutines.flow.Flow

class GetHotSalesUseCase (private val repository: PhoneStoreRepository) {

    suspend operator fun invoke(): Flow<List<HotSale>> {
        return repository.getHotSales()
    }
}
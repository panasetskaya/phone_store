package com.panasetskaia.core.domain.usecases

import com.panasetskaia.core.domain.PhoneStoreRepository
import com.panasetskaia.core.domain.entities.HotSale

class GetHotSalesUseCase (private val repository: PhoneStoreRepository) {

    suspend operator fun invoke():List<HotSale> {
        return repository.getHotSales()
    }
}
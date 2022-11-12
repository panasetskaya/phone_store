package com.panasetskaia.core.domain.usecases

import com.panasetskaia.core.domain.PhoneStoreRepository
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHotSalesUseCase @Inject constructor(private val repository: PhoneStoreRepository) {

    suspend operator fun invoke(): Flow<NetworkResult<List<HotSale>>> {
        return repository.getHotSales()
    }
}
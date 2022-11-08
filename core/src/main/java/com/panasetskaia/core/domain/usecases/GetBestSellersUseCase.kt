package com.panasetskaia.core.domain.usecases

import com.panasetskaia.core.domain.PhoneStoreRepository
import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.Phone
import kotlinx.coroutines.flow.Flow

class GetBestSellersUseCase (private val repository: PhoneStoreRepository) {

    suspend operator fun invoke(): Flow<List<BestSeller>> {
        return repository.getBestSellers()
    }

}
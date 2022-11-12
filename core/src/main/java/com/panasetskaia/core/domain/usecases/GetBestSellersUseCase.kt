package com.panasetskaia.core.domain.usecases

import com.panasetskaia.core.domain.PhoneStoreRepository
import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.NetworkResult
import com.panasetskaia.core.domain.entities.Phone
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBestSellersUseCase @Inject constructor(private val repository: PhoneStoreRepository) {

    suspend operator fun invoke(): Flow<NetworkResult<List<BestSeller>>> {
        return repository.getBestSellers()
    }

}
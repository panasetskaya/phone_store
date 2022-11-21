package com.panasetskaia.feature_details.domain

import com.panasetskaia.core.domain.PhoneStoreRepository
import com.panasetskaia.core.domain.entities.NetworkResult
import com.panasetskaia.core.domain.entities.Phone
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSinglePhoneUseCase @Inject constructor(private val repository: PhoneStoreRepository) {

    suspend operator fun invoke(): Flow<NetworkResult<Phone>> {
        return repository.getSinglePhone()
    }

}
package com.panasetskaia.core.domain.usecases

import com.panasetskaia.core.domain.PhoneStoreRepository
import com.panasetskaia.core.domain.entities.Phone
import kotlinx.coroutines.flow.Flow

class GetCartUseCase (private val repository: PhoneStoreRepository) {

    suspend operator fun invoke(): Flow<List<Phone>> {
        return repository.getCart()
    }
}
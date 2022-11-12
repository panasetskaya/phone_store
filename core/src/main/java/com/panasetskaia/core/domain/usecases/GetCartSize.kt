package com.panasetskaia.core.domain.usecases

import com.panasetskaia.core.domain.PhoneStoreRepository
import kotlinx.coroutines.flow.Flow

class GetCartSize(private val repo: PhoneStoreRepository) {

    suspend operator fun invoke(): Flow<Int> {
        return repo.getCartSize()
    }
}
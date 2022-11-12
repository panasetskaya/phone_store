package com.panasetskaia.core.domain.usecases

import com.panasetskaia.core.domain.PhoneStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartSize @Inject constructor(private val repo: PhoneStoreRepository) {

    suspend operator fun invoke(): Flow<Int> {
        return repo.getCartSize()
    }
}
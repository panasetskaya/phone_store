package com.panasetskaia.core.domain.usecases

import com.panasetskaia.core.domain.PhoneStoreRepository
import com.panasetskaia.core.domain.entities.Phone

class GetSinglePhoneUseCase (private val repository: PhoneStoreRepository) {

    suspend operator fun invoke(): Phone {
        return repository.getSinglePhone()
    }

}
package com.panasetskaia.core.domain.usecases

import com.panasetskaia.core.domain.PhoneStoreRepository
import com.panasetskaia.core.domain.entities.Phone

class GetCartUseCase (private val repository: PhoneStoreRepository) {

    suspend operator fun invoke(): List<Phone> {
        return repository.getCart()
    }
}
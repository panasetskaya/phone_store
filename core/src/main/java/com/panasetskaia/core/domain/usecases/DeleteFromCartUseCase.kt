package com.panasetskaia.core.domain.usecases

import com.panasetskaia.core.domain.PhoneStoreRepository
import com.panasetskaia.core.domain.entities.Phone
import javax.inject.Inject

class DeleteFromCartUseCase @Inject constructor(private val repository: PhoneStoreRepository) {

    suspend operator fun invoke(phone: Phone) {
        return repository.deleteFromCart(phone)
    }

}
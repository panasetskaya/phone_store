package com.panasetskaia.core.domain

import com.panasetskaia.core.domain.entities.Phone
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(private val repository: PhoneStoreRepository) {

    suspend operator fun invoke(phone: Phone) {
        return repository.addToCart(phone)
    }

}
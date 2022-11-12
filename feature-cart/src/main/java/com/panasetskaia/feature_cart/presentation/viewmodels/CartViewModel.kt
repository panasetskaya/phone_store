package com.panasetskaia.feature_cart.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.core.data.PhoneStoreRepositoryImpl
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.core.domain.usecases.AddToCartUseCase
import com.panasetskaia.core.domain.usecases.DeleteFromCartUseCase
import com.panasetskaia.core.domain.usecases.GetCartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = PhoneStoreRepositoryImpl(application)
    private val add = AddToCartUseCase(repo)
    private val delete = DeleteFromCartUseCase(repo)
    private val getCart = GetCartUseCase(repo)

    private val _cartItemsFlow = MutableStateFlow(listOf<Phone>())
    val cartItemsFlow: StateFlow<List<Phone>>
        get() = _cartItemsFlow

    init {
        getWholeCart()
    }

    private fun getWholeCart() {
        viewModelScope.launch {
            getCart().collectLatest {
                _cartItemsFlow.value = it
            }
        }
    }

    fun deleteFromCart(phone: Phone) {
        viewModelScope.launch {
            delete(phone)
        }
    }

    fun addToCart(phone: Phone) {
        viewModelScope.launch {
            add(phone)
        }
    }

    fun goToDetails() {

    }

}
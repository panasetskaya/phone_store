package com.panasetskaia.feature_cart.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.core.domain.AddToCartUseCase
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.feature_cart.domain.DeleteFromCartUseCase
import com.panasetskaia.feature_cart.domain.GetCartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(
    application: Application,
    private val add: AddToCartUseCase,
    private val delete: DeleteFromCartUseCase,
    private val getCart: GetCartUseCase
) : AndroidViewModel(application) {

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
}
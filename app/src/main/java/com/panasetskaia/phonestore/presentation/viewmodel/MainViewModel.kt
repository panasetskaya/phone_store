package com.panasetskaia.phonestore.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.NetworkResult
import com.panasetskaia.core.domain.entities.Status
import com.panasetskaia.phonestore.domain.GetBestSellersUseCase
import com.panasetskaia.phonestore.domain.GetCartSize
import com.panasetskaia.phonestore.domain.GetHotSalesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    application: Application,
    private val getHots: GetHotSalesUseCase,
    private val getBests: GetBestSellersUseCase,
    private val getCSize: GetCartSize
) : AndroidViewModel(application) {

    private val _hotSalesStateFlow = MutableStateFlow(
        NetworkResult(Status.LOADING, listOf<HotSale>(), "")
    )
    val hotSalesStateFlow: StateFlow<NetworkResult<List<HotSale>>>
        get() = _hotSalesStateFlow

    private val _bestSellersStateFlow = MutableStateFlow(
        NetworkResult(Status.LOADING, listOf<BestSeller>(), "")
    )
    val bestSellersStateFlow: StateFlow<NetworkResult<List<BestSeller>>>
        get() = _bestSellersStateFlow

    private val _cartSizeFlow = MutableStateFlow(
        0
    )
    val cartSizeFlow: StateFlow<Int>
        get() = _cartSizeFlow

    init {
        getNewHotSales()
        getNewBestSellers()
        getCartSize()
    }

    private fun getCartSize() {
        viewModelScope.launch {
            getCSize().collect {
                _cartSizeFlow.value = it
            }
        }
    }

    private fun getNewHotSales() {
        _hotSalesStateFlow.value = NetworkResult.loading()
        viewModelScope.launch {
            getHots().collect {
                when (it.status) {
                    Status.ERROR -> {
                        _hotSalesStateFlow.value = NetworkResult.error(it.message.toString())
                    }
                    Status.SUCCESS -> {
                        _hotSalesStateFlow.value = NetworkResult.success(it.data)
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun getNewBestSellers() {
        _bestSellersStateFlow.value = NetworkResult.loading()
        viewModelScope.launch {
            getBests().collect {
                when (it.status) {
                    Status.ERROR -> {
                        _bestSellersStateFlow.value = NetworkResult.error(it.message.toString())
                    }
                    Status.SUCCESS -> {
                        _bestSellersStateFlow.value = NetworkResult.success(it.data)
                    }
                    else -> {
                    }
                }
            }
        }
    }
}



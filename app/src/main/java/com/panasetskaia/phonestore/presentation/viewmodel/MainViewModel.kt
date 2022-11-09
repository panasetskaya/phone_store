package com.panasetskaia.phonestore.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.core.data.PhoneStoreRepositoryImpl
import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.NetworkResult
import com.panasetskaia.core.domain.entities.Status
import com.panasetskaia.core.domain.usecases.GetBestSellersUseCase
import com.panasetskaia.core.domain.usecases.GetHotSalesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = PhoneStoreRepositoryImpl(application)
    private val getHots = GetHotSalesUseCase(repo)
    private val getBests = GetBestSellersUseCase(repo)

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

    init {
        getNewHotSales()
        getNewBestSellers()
    }

    fun getNewHotSales() {
        _hotSalesStateFlow.value = NetworkResult.loading()
        viewModelScope.launch {
            getHots()
                .catch {
                    _hotSalesStateFlow.value = NetworkResult.error(it.message.toString())
                }
                .collect {
                    _hotSalesStateFlow.value = NetworkResult.success(it.data)
                }
        }
    }

    fun getNewBestSellers() {
        _bestSellersStateFlow.value = NetworkResult.loading()
        viewModelScope.launch {
            getBests()
                .catch {
                    _bestSellersStateFlow.value = NetworkResult.error(it.message.toString())
                }
                .collect {
                    _bestSellersStateFlow.value = NetworkResult.success(it.data)
                }
        }
    }
}



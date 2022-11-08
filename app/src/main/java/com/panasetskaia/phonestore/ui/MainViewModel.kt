package com.panasetskaia.phonestore.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.core.data.PhoneStoreRepositoryImpl
import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.core.domain.usecases.GetBestSellersUseCase
import com.panasetskaia.core.domain.usecases.GetHotSalesUseCase
import com.panasetskaia.core.domain.usecases.GetSinglePhoneUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repo = PhoneStoreRepositoryImpl(application)
    private val getAPhone = GetSinglePhoneUseCase(repo)
    private val getHots = GetHotSalesUseCase(repo)
    private val getBests = GetBestSellersUseCase(repo)

    private val _phoneFlow = MutableSharedFlow<Phone>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _hotSalesFlow = MutableSharedFlow<List<HotSale>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _bestSellersFlow = MutableSharedFlow<List<BestSeller>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun getPhone(): SharedFlow<Phone> {
        viewModelScope.launch {
            _phoneFlow.emitAll(getAPhone())
        }
        return _phoneFlow
    }

    fun getHotSales(): SharedFlow<List<HotSale>> {
        viewModelScope.launch {
            _hotSalesFlow.emitAll(getHots())
        }
        return _hotSalesFlow
    }

    fun getBestSellers(): SharedFlow<List<BestSeller>> {
        viewModelScope.launch {
            _bestSellersFlow.emitAll(getBests())
        }
        return _bestSellersFlow
    }
}
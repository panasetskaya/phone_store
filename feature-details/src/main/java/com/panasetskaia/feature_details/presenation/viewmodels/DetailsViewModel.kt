package com.panasetskaia.feature_details.presenation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.core.domain.entities.NetworkResult
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.core.domain.entities.Status
import com.panasetskaia.core.domain.usecases.AddToCartUseCase
import com.panasetskaia.core.domain.usecases.GetSinglePhoneUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    application: Application,
    private val getPhone: GetSinglePhoneUseCase,
    private val add: AddToCartUseCase
) : AndroidViewModel(application) {


    private val _phoneStateFlow = MutableStateFlow(
        NetworkResult(Status.LOADING, Phone(0), "")
    )
    val phoneStateFlow: StateFlow<NetworkResult<Phone>>
        get() = _phoneStateFlow

    init {
        getNewPhone()
    }

    fun addToCart(newPhone: Phone) {
        viewModelScope.launch {
            add(newPhone)
        }
    }


    private fun getNewPhone() {
        _phoneStateFlow.value = NetworkResult.loading()
        viewModelScope.launch {
            getPhone()
                .collect {
                    when (it.status) {
                        Status.ERROR -> {
                            _phoneStateFlow.value = NetworkResult.error(it.message.toString())
                        }
                        Status.SUCCESS -> {
                            _phoneStateFlow.value = NetworkResult.success(it.data)
                        }
                        else -> {
                        }
                    }
                }
        }
    }
}
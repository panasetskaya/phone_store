package com.panasetskaia.feature_details.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.core.data.PhoneStoreRepositoryImpl
import com.panasetskaia.core.domain.entities.NetworkResult
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.core.domain.entities.Status
import com.panasetskaia.core.domain.usecases.GetSinglePhoneUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = PhoneStoreRepositoryImpl(application)
    private val getPhone = GetSinglePhoneUseCase(repo)

    private val _chosenColorFlow = MutableStateFlow("")
    val chosenColorFlow: StateFlow<String>
        get() = _chosenColorFlow

    private val _chosenCapacityFlow = MutableStateFlow("")
    val chosenCapacityFlow: StateFlow<String>
        get() = _chosenCapacityFlow

    private val _phoneStateFlow = MutableStateFlow(
        NetworkResult(Status.LOADING, Phone(), "")
    )
    val phoneStateFlow: StateFlow<NetworkResult<Phone>>
        get() = _phoneStateFlow

    init {
        getNewPhone()
    }

    fun getNewPhone() {
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
                            _chosenColorFlow.value = it.data?.colors?.get(0) ?: ""
                            _chosenCapacityFlow.value = it.data?.capacities?.get(0) ?: ""
                        }
                        else -> {
                        }
                    }
                }
        }
    }

    fun changeColor(color: String) {
        _chosenColorFlow.value = color
    }

    fun changeCapacity(capacity: String) {
        _chosenCapacityFlow.value = capacity
    }
}
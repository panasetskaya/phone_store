package com.panasetskaia.feature_details.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.core.data.PhoneStoreRepositoryImpl
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.core.domain.entities.NetworkResult
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.core.domain.entities.Status
import com.panasetskaia.core.domain.usecases.GetSinglePhoneUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = PhoneStoreRepositoryImpl(application)
    private val getPhone = GetSinglePhoneUseCase(repo)

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
                .catch {
                    _phoneStateFlow.value = NetworkResult.error(it.message.toString())
                }
                .collect {
                    _phoneStateFlow.value = NetworkResult.success(it.data)
                }
        }
    }
}
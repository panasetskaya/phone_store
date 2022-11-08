package com.panasetskaia.phonestore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.core.data.PhoneStoreRepositoryImpl
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.core.domain.usecases.GetSinglePhoneUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val repo = PhoneStoreRepositoryImpl()
    val getAPhone = GetSinglePhoneUseCase(repo)

    private val _phoneFlow = MutableSharedFlow<Phone>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun getPhone(): SharedFlow<Phone> {
        viewModelScope.launch {
            _phoneFlow.emitAll(getAPhone())
        }
        return _phoneFlow
    }
}
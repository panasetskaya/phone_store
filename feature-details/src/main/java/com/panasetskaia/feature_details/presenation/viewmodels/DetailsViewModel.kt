package com.panasetskaia.feature_details.presenation.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.panasetskaia.core.data.PhoneStoreRepositoryImpl
import com.panasetskaia.core.domain.entities.NetworkResult
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.core.domain.entities.Status
import com.panasetskaia.core.domain.usecases.AddToCartUseCase
import com.panasetskaia.core.domain.usecases.GetCartSize
import com.panasetskaia.core.domain.usecases.GetSinglePhoneUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = PhoneStoreRepositoryImpl(application)
    private val getPhone = GetSinglePhoneUseCase(repo)
    private val add = AddToCartUseCase(repo)

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
                        }
                        else -> {
                        }
                    }
                }
        }
    }

    fun setTestingPhone() {
        val phone = Phone(
            0,
            images = listOf(
                "https://www.notebookcheck-ru.com/uploads/tx_nbc2/OppoFindX2Pro.JPG",
                "https://www.ixbt.com/img/n1/news/2020/1/6/oppo-find-x2-pro-live-images-71_large.jpg"
            ),
            title = "Gorgeous SmartPhone",
            price = 2222,
            rating = 4.3f,
            camera = "mock camera",
            CPU = "mock cpu",
            sd = "mock sd",
            ssd = "mock ssd",
            capacities = listOf("256", "128", "133", "000"),
            colors = listOf("#eb4034", "#e8b8b5", "#d6cd22")
        )
        _phoneStateFlow.value = NetworkResult(Status.SUCCESS, phone, null)
    }
}
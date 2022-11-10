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

    private var chosenColor: String? =null
    private var chosenCapacity: String? =null

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
                        }
                        else -> {
                        }
                    }
                }
        }
    }

    fun changeColor(color: String) {
        chosenColor = color
    }

    fun changeCapacity(capacity: String) {
        chosenCapacity = capacity
    }

    fun setTestingPhone() {
        val phone = Phone(
            images = listOf("https://www.notebookcheck-ru.com/uploads/tx_nbc2/OppoFindX2Pro.JPG",
                "https://www.ixbt.com/img/n1/news/2020/1/6/oppo-find-x2-pro-live-images-71_large.jpg"),
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
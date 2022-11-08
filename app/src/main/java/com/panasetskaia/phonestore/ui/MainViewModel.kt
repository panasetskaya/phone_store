package com.panasetskaia.phonestore.ui

import androidx.lifecycle.ViewModel
import com.panasetskaia.core.data.PhoneStoreRepositoryImpl

class MainViewModel: ViewModel() {

    val repo = PhoneStoreRepositoryImpl()

}
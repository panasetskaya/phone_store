package com.panasetskaia.phonestore.di

import androidx.lifecycle.ViewModel
import com.panasetskaia.core.di.ViewModelKey
import com.panasetskaia.phonestore.presentation.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindCartViewModel(impl: MainViewModel): ViewModel
}
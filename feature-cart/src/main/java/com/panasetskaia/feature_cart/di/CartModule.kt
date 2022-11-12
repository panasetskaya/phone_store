package com.panasetskaia.feature_cart.di

import androidx.lifecycle.ViewModel
import com.panasetskaia.core.di.ViewModelKey
import com.panasetskaia.feature_cart.presentation.ui.CartFragment
import com.panasetskaia.feature_cart.presentation.viewmodels.CartViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CartModule {

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    fun bindCartViewModel(impl: CartViewModel): ViewModel

}
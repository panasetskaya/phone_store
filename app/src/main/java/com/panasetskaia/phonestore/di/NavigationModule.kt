package com.panasetskaia.phonestore.di

import com.panasetskaia.feature_cart.navigation.CartNavCommandProvider
import com.panasetskaia.feature_details.navigation.DetailsNavCommandProvider
import com.panasetskaia.phonestore.navigation.CartNavCommandProviderImpl
import com.panasetskaia.phonestore.navigation.DetailsNavCommandProviderimpl
import dagger.Binds
import dagger.Module

@Module
interface NavigationModule {

    @Binds
    fun bindCartNavigator(impl: CartNavCommandProviderImpl): CartNavCommandProvider

    @Binds
    fun bindDetailsNavigator(impl: DetailsNavCommandProviderimpl): DetailsNavCommandProvider

}
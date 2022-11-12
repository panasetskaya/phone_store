package com.panasetskaia.phonestore.application

import android.app.Application
import com.panasetskaia.feature_cart.di.CartComponent
import com.panasetskaia.feature_cart.di.CartComponentProvider
import com.panasetskaia.feature_details.di.DetailsComponent
import com.panasetskaia.feature_details.di.DetailsComponentProvider
import com.panasetskaia.phonestore.di.AppComponent
import com.panasetskaia.phonestore.di.DaggerAppComponent

class StoreApplication: Application(), CartComponentProvider, DetailsComponentProvider {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create(this)
    }

    override fun getCartComponent(): CartComponent {
        return component
    }

    override fun getDetailsComponent(): DetailsComponent {
        return component
    }
}
package com.panasetskaia.phonestore.di

import android.app.Application
import com.panasetskaia.core.di.CoreModule
import com.panasetskaia.core.di.StoreAppScope
import com.panasetskaia.feature_cart.di.CartComponent
import com.panasetskaia.feature_cart.di.CartModule
import com.panasetskaia.feature_cart.presentation.ui.CartFragment
import com.panasetskaia.feature_details.di.DetailsComponent
import com.panasetskaia.feature_details.di.DetailsModule
import com.panasetskaia.feature_details.presenation.ui.DetailsFragment
import com.panasetskaia.feature_details.presenation.ui.TabShopFragment
import com.panasetskaia.phonestore.presentation.ui.MainFragment
import dagger.BindsInstance
import dagger.Component

@StoreAppScope
@Component(modules = [CoreModule::class, CartModule::class, DetailsModule::class, MainModule::class])
interface AppComponent: CartComponent, DetailsComponent {

    override fun injectDetailsFragment(fragment: DetailsFragment)

    override fun injectCartFragment(fragment: CartFragment)

    override fun injectTabShopFragment(fragment: TabShopFragment)

    fun injectMainFragment(mainFragment: MainFragment)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance application: Application): AppComponent
    }

}
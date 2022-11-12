package com.panasetskaia.feature_cart.di

import com.panasetskaia.feature_cart.presentation.ui.CartFragment

interface CartComponent {

    fun injectCartFragment(fragment: CartFragment)

}
package com.panasetskaia.phonestore.navigation

import com.panasetskaia.core.navigation.NavCommand
import com.panasetskaia.feature_cart.navigation.CartNavCommandProvider
import com.panasetskaia.phonestore.R

class CartNavCommandProviderImpl: CartNavCommandProvider {
    override val toDetails: NavCommand
        get() = NavCommand(R.id.action_cartFragment_to_detailsFragment)
}
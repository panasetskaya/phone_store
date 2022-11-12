package com.panasetskaia.phonestore.navigation

import com.panasetskaia.core.navigation.NavCommand
import com.panasetskaia.feature_cart.navigation.CartNavCommandProvider
import com.panasetskaia.phonestore.R
import javax.inject.Inject

class CartNavCommandProviderImpl @Inject constructor(): CartNavCommandProvider {
    override val toDetails: NavCommand
        get() = NavCommand(R.id.action_cartFragment_to_detailsFragment)
    override val navHost: Int
        get() = R.id.fcvMain
}
package com.panasetskaia.phonestore.navigation

import com.panasetskaia.core.navigation.NavCommand
import com.panasetskaia.feature_details.navigation.DetailsNavCommandProvider
import com.panasetskaia.phonestore.R

class DetailsNavCommandProviderimpl: DetailsNavCommandProvider {
    override val toCart: NavCommand
        get() = NavCommand(R.id.action_detailsFragment_to_cartFragment)
}
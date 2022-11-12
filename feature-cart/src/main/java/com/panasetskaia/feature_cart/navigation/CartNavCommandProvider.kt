package com.panasetskaia.feature_cart.navigation

import com.panasetskaia.core.navigation.NavCommand

interface CartNavCommandProvider {
    val toDetails: NavCommand
    val navHost: Int
}
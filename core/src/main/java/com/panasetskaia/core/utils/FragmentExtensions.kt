package com.panasetskaia.core.utils

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.panasetskaia.core.navigation.NavCommand

fun Fragment.navigate(navCommand: NavCommand, hostId: Int? = null) {
    val navController = if (hostId == null) {
        findNavController()
    } else {
        Navigation.findNavController(requireActivity(), hostId)
    }
    navController.navigate(navCommand.action, navCommand.args, navCommand.navOptions)
}
package com.panasetskaia.feature_details.di

import com.panasetskaia.feature_details.presenation.ui.DetailsFragment
import com.panasetskaia.feature_details.presenation.ui.TabShopFragment

interface DetailsComponent {
    fun injectDetailsFragment(fragment: DetailsFragment)
    fun injectTabShopFragment(fragment: TabShopFragment)
}
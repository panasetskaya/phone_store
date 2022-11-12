package com.panasetskaia.feature_details.presenation.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.panasetskaia.feature_details.presenation.ui.TabDefaultFragment
import com.panasetskaia.feature_details.presenation.ui.TabShopFragment

class ParentCategoryPagerAdapter(
    parentFragment: Fragment
) : FragmentStateAdapter(parentFragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabShopFragment()
            1 -> TabDefaultFragment()
            else -> TabDefaultFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}
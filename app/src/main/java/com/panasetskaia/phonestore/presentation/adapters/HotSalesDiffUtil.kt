package com.panasetskaia.phonestore.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.panasetskaia.core.domain.entities.HotSale

class HotSalesDiffUtil: DiffUtil.ItemCallback<HotSale>() {
    override fun areItemsTheSame(oldItem: HotSale, newItem: HotSale): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HotSale, newItem: HotSale): Boolean {
        return oldItem == newItem
    }
}
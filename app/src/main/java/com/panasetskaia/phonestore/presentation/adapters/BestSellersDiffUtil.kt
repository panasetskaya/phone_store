package com.panasetskaia.phonestore.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.panasetskaia.core.domain.entities.BestSeller

class BestSellersDiffUtil: DiffUtil.ItemCallback<BestSeller>() {
    override fun areItemsTheSame(oldItem: BestSeller, newItem: BestSeller): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BestSeller, newItem: BestSeller): Boolean {
        return oldItem == newItem
    }
}
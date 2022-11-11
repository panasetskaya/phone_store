package com.panasetskaia.feature_cart.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.panasetskaia.core.domain.entities.Phone

class PhoneDiffUtil: DiffUtil.ItemCallback<Phone>() {
    override fun areItemsTheSame(oldItem: Phone, newItem: Phone): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Phone, newItem: Phone): Boolean {
        return oldItem == newItem
    }
}
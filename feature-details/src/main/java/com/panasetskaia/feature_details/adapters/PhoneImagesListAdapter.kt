package com.panasetskaia.feature_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panasetskaia.feature_details.databinding.CarouselItemLayoutBinding

class PhoneImagesListAdapter: ListAdapter<String, PhoneImagesListAdapter.PhoneViewholder>(StringDiffUtil()) {

    class PhoneViewholder(val binding: CarouselItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewholder {
        val binding = CarouselItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return PhoneViewholder(binding)
    }

    override fun onBindViewHolder(holder: PhoneViewholder, position: Int) {
        val item = getItem(position)
        Glide.with(holder.binding.root.context).load(item)
            .placeholder(com.panasetskaia.core.R.drawable.img)
            .into(holder.binding.carouselImage)
    }
}
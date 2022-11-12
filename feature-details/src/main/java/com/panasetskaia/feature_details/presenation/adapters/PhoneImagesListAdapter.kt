package com.panasetskaia.feature_details.presenation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panasetskaia.feature_details.databinding.CarouselItemLayoutBinding

class PhoneImagesListAdapter: ListAdapter<String, PhoneImagesListAdapter.PhoneViewHolder>(
    StringDiffUtil()) {

    class PhoneViewHolder(val binding: CarouselItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val binding = CarouselItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return PhoneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        val item = getItem(position)
        Glide.with(holder.binding.root.context).load(item)
            .placeholder(com.panasetskaia.core.R.drawable.img)
            .into(holder.binding.carouselImage)
    }
}
package com.panasetskaia.phonestore.presentation.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panasetskaia.core.domain.entities.BestSeller
import com.panasetskaia.phonestore.R
import com.panasetskaia.phonestore.databinding.BestSellerCardBinding

class BestSellersListAdapter: ListAdapter<BestSeller, BestSellersListAdapter.BestSellersViewHolder>(BestSellersDiffUtil()) {

    var onItemClickListener: ((BestSeller) -> Unit)? = null

    class BestSellersViewHolder(val binding: BestSellerCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellersViewHolder {
        val binding = BestSellerCardBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return BestSellersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BestSellersViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(item)
            true
        }
        with(holder.binding) {
            Glide.with(root.context).load(item.picUrl)
                .placeholder(R.drawable.img)
                .into(bestsellerImage)
            bestsellerActualPrice.text = item.discountPrice.toString() + "$"
            bestsellerOldPrice.text = item.noDiscountPrice.toString() + "$"
            bestsellerOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            bestsellerTitle.text = item.title
            if (item.isFav) {
                isFavorite.visibility = View.VISIBLE
                notFavorite.visibility = View.GONE
            } else {
                isFavorite.visibility = View.GONE
                notFavorite.visibility = View.VISIBLE
            }
            isFavorite.setOnClickListener {
                isFavorite.visibility = View.GONE
                notFavorite.visibility = View.VISIBLE
            }
            notFavorite.setOnClickListener {
                isFavorite.visibility = View.VISIBLE
                notFavorite.visibility = View.GONE
            }
        }
    }
}
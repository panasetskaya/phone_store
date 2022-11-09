package com.panasetskaia.phonestore.presentation.adapters

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
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
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: com.bumptech.glide.request.target.Target<Drawable>?, p3: Boolean): Boolean {
                        return false
                    }
                    override fun onResourceReady(p0: Drawable?, p1: Any?, p2: com.bumptech.glide.request.target.Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                        bestsellerProgressBar.visibility = View.GONE
                        return false
                    }
                })
                .placeholder(com.panasetskaia.core.R.drawable.img)
                .into(bestsellerImage)
            bestsellerActualPrice.text = item.discountPrice.toString() + "$"
            bestsellerOldPrice.text = item.noDiscountPrice.toString() + "$"
            bestsellerOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            bestsellerTitle.text = item.title
            if (item.isFav==true) {
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
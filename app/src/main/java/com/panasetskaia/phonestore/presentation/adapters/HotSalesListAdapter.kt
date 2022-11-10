package com.panasetskaia.phonestore.presentation.adapters

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
import com.panasetskaia.core.domain.entities.HotSale
import com.panasetskaia.phonestore.databinding.HotSalesCardBinding


class HotSalesListAdapter :
    ListAdapter<HotSale, HotSalesListAdapter.HotSalesViewHolder>(HotSalesDiffUtil()) {

    var onItemClickListener: ((HotSale) -> Unit)? = null

    class HotSalesViewHolder(val binding: HotSalesCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSalesViewHolder {
        val binding = HotSalesCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HotSalesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotSalesViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(item)
            true
        }
        with(holder.binding) {
            Glide.with(root.context)
                .load(item.picUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: com.bumptech.glide.request.target.Target<Drawable>?, p3: Boolean): Boolean {
                        hotSalesProgressBar.visibility = View.GONE
                        return false
                    }
                    override fun onResourceReady(p0: Drawable?, p1: Any?, p2: com.bumptech.glide.request.target.Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                        hotSalesProgressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(hotSaleImage)
            hotSaleTitle.text = item.title
            hotSaleSubtitle.text = item.subtitle
            if (item.isNew == true) {
                isNew.visibility = View.VISIBLE
            } else {
                isNew.visibility = View.GONE
            }
            buyNowButton.setOnClickListener {
                onItemClickListener?.invoke(item)
                true
            }
        }
    }
}
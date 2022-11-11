package com.panasetskaia.feature_cart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.feature_cart.databinding.CartItemLayoutBinding
import com.panasetskaia.feature_cart.viewmodels.CartViewModel

class PhoneListAdapter(private val viewModel: CartViewModel): ListAdapter<Phone, PhoneListAdapter.PhoneViewHolder>(PhoneDiffUtil()) {

    class PhoneViewHolder(val binding: CartItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val binding = CartItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return PhoneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            ivDelete.setOnClickListener {
                viewModel.deleteFromCart(item)
            }
            tvIncrement.setOnClickListener {
                var currentQuantity = item.quantity
                val incremented = ++currentQuantity
                val newPhone = item.copy(quantity = incremented)
                viewModel.addToCart(newPhone)
            }
            tvDecrement.setOnClickListener {
                var currentQuantity = item.quantity
                val decremented = --currentQuantity
                if (decremented > 0) {
                    val newPhone = item.copy(quantity = decremented)
                    viewModel.addToCart(newPhone)
                } else {
                    viewModel.deleteFromCart(item)
                }

            }
            tvCartItemTitle.text = item.title
            tvCartItemPrice.text = "$"+ "${item.price?.times(item.quantity) ?: 0}" + ".00"
            imageViewCardView.setOnClickListener{
                viewModel.goToDetails()
            }
            Glide.with(root.context).load(item.images?.get(0))
                .placeholder(com.panasetskaia.core.R.drawable.img)
                .into(imageViewCartItem)
        }
    }
}
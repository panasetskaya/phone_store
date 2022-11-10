package com.panasetskaia.feature_details.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.panasetskaia.feature_details.databinding.CapacityItemBinding
import com.panasetskaia.feature_details.ui.TabShopFragment
import com.panasetskaia.feature_details.viewmodels.DetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CapacitiesListAdapter(val viewModel: DetailsViewModel, val parentFragment: TabShopFragment) :
    ListAdapter<String, CapacitiesListAdapter.CapacitiesViewholder>(StringDiffUtil())  {

    var onItemClickListener: ((String) -> Unit)? = null

    var chosenCapacity = ""

    init {
        collectFlows()
    }

    class CapacitiesViewholder(val binding: CapacityItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapacitiesViewholder {
        val binding = CapacityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CapacitiesViewholder(binding)
    }

    override fun onBindViewHolder(holder: CapacitiesViewholder, position: Int) {
        val item = getItem(position)
        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(item)
            true
        }
        holder.binding.capacityVariantText.text = item
        if (item == chosenCapacity) {
            holder.binding.capacityVariantText.setTextColor(com.panasetskaia.core.R.color.white)
            holder.binding.capacityVariantCard.backgroundTintList = ColorStateList.valueOf(com.panasetskaia.core.R.color.peach_color)
        } else {
            holder.binding.capacityVariantText.setTextColor(com.panasetskaia.core.R.color.darker_gray)
            holder.binding.capacityVariantCard.backgroundTintList = ColorStateList.valueOf(com.panasetskaia.core.R.color.white)
        }
    }

    private fun collectFlows() {
        parentFragment.viewLifecycleOwner.lifecycleScope.launch {
            parentFragment.viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.chosenCapacityFlow.collectLatest {
                    chosenCapacity = it
                }
            }
        }
    }
}
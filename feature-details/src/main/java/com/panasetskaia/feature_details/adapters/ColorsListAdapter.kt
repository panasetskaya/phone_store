package com.panasetskaia.feature_details.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.panasetskaia.feature_details.databinding.ColorItemBinding
import com.panasetskaia.feature_details.ui.TabShopFragment
import com.panasetskaia.feature_details.viewmodels.DetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ColorsListAdapter(val viewModel: DetailsViewModel, val parentFragment: TabShopFragment) :
    ListAdapter<String, ColorsListAdapter.ColorsViewholder>(StringDiffUtil()) {

    var onItemClickListener: ((String) -> Unit)? = null

    var chosenColor = ""

    init {
        collectFlows()
    }

    class ColorsViewholder(val binding: ColorItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewholder {
        val binding = ColorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorsViewholder(binding)
    }

    override fun onBindViewHolder(holder: ColorsViewholder, position: Int) {
        val item = getItem(position)
        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(item)
            true
        }
        val color = Color.parseColor(item)
        holder.binding.colorFab.setBackgroundColor(color)
        if (item == chosenColor) {
            holder.binding.colorFab.imageTintList = ColorStateList.valueOf(com.panasetskaia.core.R.color.white)
        } else {
            holder.binding.colorFab.imageTintList = ColorStateList.valueOf(color)
        }
    }

    private fun collectFlows() {
        parentFragment.viewLifecycleOwner.lifecycleScope.launch {
            parentFragment.viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.chosenColorFlow.collectLatest {
                    chosenColor = it
                }
            }
        }
    }
}
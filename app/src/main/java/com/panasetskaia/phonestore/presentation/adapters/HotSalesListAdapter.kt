package com.panasetskaia.phonestore.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class HotSalesListAdapter {

    class HotSalesViewHolder(val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root)

}

//class DictionaryListAdapter :
//    ListAdapter<ChineseCharacter, ChineseCharViewHolder>(ChineseCharDiffUtilCallback()) {
//
//    var onCharacterItemClickListener: ((ChineseCharacter) -> Unit)? = null
//    var onCharacterItemLongClickListener: ((ChineseCharacter) -> Unit)? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChineseCharViewHolder {
//        val binding = when (viewType) {
//            CHOSEN -> CharacterItemChosenBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//            NOT_CHOSEN -> CharacterItemNotChosenBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//            else -> throw RuntimeException("No such viewType: $viewType!")
//        }
//        return ChineseCharViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ChineseCharViewHolder, position: Int) {
//        val item = getItem(position)
//        val binding = holder.binding
//        binding.root.setOnLongClickListener {
//            onCharacterItemLongClickListener?.invoke(item)
//            true
//        }
//        binding.root.setOnClickListener {
//            onCharacterItemClickListener?.invoke(item)
//            true
//        }
//
//        when (binding) {
//            is CharacterItemChosenBinding -> {
//                with(binding) {
//                    tvCharacterChinese.text = item.character
//                    tvPinyin.text = item.pinyin
//                    val translationCut =
//                        if (item.translation.length <= 21) item.translation else item.translation.substring(
//                            0..21
//                        ) + "..."
//                    tvTranslation.text = translationCut
//                }
//            }
//            is CharacterItemNotChosenBinding -> {
//                with(binding) {
//                    tvCharacterChinese.text = item.character
//                    tvPinyin.text = item.pinyin
//                    val translationCut =
//                        if (item.translation.length <= 21) item.translation else item.translation.substring(
//                            0..21
//                        ) + "..."
//                    tvTranslation.text = translationCut
//                }
//            }
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        val item = getItem(position)
//        return if (item.isChosen) CHOSEN
//        else NOT_CHOSEN
//    }
//
//    companion object {
//        const val CHOSEN = 1
//        const val NOT_CHOSEN = 0
//        const val MAX_POOL_SIZE = 15
//    }
//}
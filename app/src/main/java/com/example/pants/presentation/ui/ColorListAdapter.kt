package com.example.pants.presentation.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pants.databinding.ItemColorBinding
import com.example.pants.domain.model.ColorModel
import com.example.pants.utils.extension.swap

class ColorListAdapter(
    private val onItemClicked: (ColorModel) -> Unit,
) : ListAdapter<ColorModel, ColorListAdapter.ColorViewHolder>(DIFF_UTIL) {

    private var originalList: List<ColorModel> = emptyList()
    private var isInHelpMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ColorViewHolder(
            binding = ItemColorBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) =
        holder.bind(getItem(position), isInHelpMode, onItemClicked, ::moveItemUp)

    fun setOriginalList() {
        originalList = currentList
    }

    fun resetToOriginalOrder() = submitList(originalList)

    @SuppressLint("NotifyDataSetChanged")
    fun setHelpMode(isHelpMode: Boolean) {
        isInHelpMode = isHelpMode
        notifyDataSetChanged()
    }

    private fun moveItemUp(position: Int) {
        val itemList = currentList.toMutableList()
        val newPosition = if (position > 0) position - 1 else currentList.size - 1
        itemList.swap(position, newPosition)
        submitList(itemList)
    }

    class ColorViewHolder(private val binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: ColorModel,
            isHelpMode: Boolean,
            onItemClicked: (ColorModel) -> Unit,
            onMoveItemUp: (Int) -> Unit
        ) {
            with(binding) {
                colorCard.backgroundTintList = ColorStateList.valueOf(calculateColor(item, isHelpMode))
                colorName.text = item.name
                colorCard.setOnClickListener { onItemClicked(item) }
                btnMoveUp.setOnClickListener { onMoveItemUp(bindingAdapterPosition) }
                btnMoveUp.isVisible = !isHelpMode
            }
        }

        private fun calculateColor(item: ColorModel, isHelpMode: Boolean): Int {
            return if (isHelpMode) {
                Color.hsv(
                    hue = item.realHue * 360f,
                    saturation = 1f,
                    value = 1f
                ).toArgb()
            } else if (item.guessHue != null) {
                Color.hsv(
                    hue = item.guessHue,
                    saturation = 1f,
                    value = 1f
                ).toArgb()
            } else {
                Color.Gray.toArgb()
            }
        }
    }

    private companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ColorModel>() {
            override fun areItemsTheSame(oldItem: ColorModel, newItem: ColorModel): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: ColorModel, newItem: ColorModel): Boolean =
                oldItem == newItem
        }
    }
}

package com.kryptkode.template.categories.model

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by kryptkode on 3/2/2020.
 */
data class CategoryForView(
    val id: String,
    val name: String,
    val imageUrl: String,
    val favorite: Boolean,
    val sortOrder: String,
    val status: String,
    val locked: Boolean
) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CategoryForView>() {
            override fun areContentsTheSame(
                oldItem: CategoryForView,
                newItem: CategoryForView
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(
                oldItem: CategoryForView,
                newItem: CategoryForView
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
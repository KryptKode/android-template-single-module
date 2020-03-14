package com.kryptkode.template.subcategories.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

/**
 * Created by kryptkode on 3/8/2020.
 */
@Parcelize
data class SubCategoryForView(
    val id: String,
    val name: String,
    val categoryId: String,
    val imageUrl: String,
    val status: String,
    val sortOrder: String,
    val favorite: Boolean
) : Parcelable {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SubCategoryForView>() {
            override fun areContentsTheSame(
                oldItem: SubCategoryForView,
                newItem: SubCategoryForView
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: SubCategoryForView,
                newItem: SubCategoryForView
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
package com.kryptkode.template.cardlist.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

/**
 * Created by kryptkode on 3/12/2020.
 */
@Parcelize
data class CardForView(
    val id: String,
    val name: String,
    val categoryId: String,
    val subcategoryId: String,
    val imageUrl: String,
    val status: String,
    val favorite: Boolean
) : Parcelable {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CardForView>() {
            override fun areContentsTheSame(oldItem: CardForView, newItem: CardForView): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: CardForView, newItem: CardForView): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
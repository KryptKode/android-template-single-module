package com.kryptkode.template.app.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by kryptkode on 2/19/2020.
 */

@Entity
data class CardEntity(
    @PrimaryKey val id: String,
    val name: String,
    val categoryId: String,
    val subcategoryId: String,
    val imgUrl: String,
    val status: String,
    val favorite: Boolean
)
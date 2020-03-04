package com.kryptkode.template.app.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by kryptkode on 2/19/2020.
 */

@Entity
data class SubCategoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val parentId: String,
    val imageUrl: String,
    val status: String,
    val sortOrder: String,
    val favorite: Boolean
)
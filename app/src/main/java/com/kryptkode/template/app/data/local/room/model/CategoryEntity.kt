package com.kryptkode.template.app.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by kryptkode on 2/19/2020.
 */

@Entity
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val favorite: Boolean,
    val status: String,
    val sort: String,
    val locked: Boolean
)
package com.kryptkode.template.app.data.domain.model

/**
 * Created by kryptkode on 2/19/2020.
 */
data class Category(
    val id: String,
    val name: String,
    val imageUrl: String,
    val status: String,
    val sortOrder: String,
    val favorite: Boolean,
    val locked: Boolean
)
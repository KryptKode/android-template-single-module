package com.kryptkode.template.categories.model

/**
 * Created by kryptkode on 3/2/2020.
 */
data class CategoryForView(
    val id: String,
    val name: String,
    val imageUrl: String,
    val favorite: Boolean,
    val sortOrder:String,
    val status: String,
    val locked: Boolean
)
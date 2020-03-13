package com.kryptkode.template.app.data.domain.model

/**
 * Created by kryptkode on 2/19/2020.
 */
data class Card(
    val id: String,
    val name: String,
    val categoryId: String,
    val subcategoryId: String,
    val imageUrl: String,
    val status: String,
    val favorite: Boolean
)
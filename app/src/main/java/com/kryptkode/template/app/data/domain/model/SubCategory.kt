package com.kryptkode.template.app.data.domain.model

/**
 * Created by kryptkode on 2/19/2020.
 */
data class SubCategory(
    val id: String,
    val name: String,
    val parentId: String,
    val imageUrl: String,
    val status: String,
    val sort: String,
    val favorite: Boolean
)
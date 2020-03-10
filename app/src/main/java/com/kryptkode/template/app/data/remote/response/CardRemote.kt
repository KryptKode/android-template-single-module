package com.kryptkode.template.app.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by kryptkode on 2/19/2020.
 */
@JsonClass(generateAdapter = true)
data class CardRemote(
    @field:Json(name="id")
    val id: String,
    @field:Json(name="name")
    val name: String,
    @field:Json(name="category")
    val categoryId: String,
    @field:Json(name="sub_category")
    val subcategoryId: String,
    @field:Json(name="link")
    val imgUrl: String,
    @field:Json(name="status")
    val status: String
)
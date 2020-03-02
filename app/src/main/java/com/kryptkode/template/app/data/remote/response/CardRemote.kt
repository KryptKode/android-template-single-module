package com.kryptkode.template.app.data.remote.response

import com.squareup.moshi.Json

/**
 * Created by kryptkode on 2/19/2020.
 */
data class CardRemote(
    @Json(name="id")
    val id: String,
    @Json(name="name")
    val name: String,
    @Json(name="category")
    val categoryId: String,
    @Json(name="sub_category")
    val subcategoryId: String,
    @Json(name="link")
    val imgUrl: String,
    @Json(name="status")
    val status: String
)
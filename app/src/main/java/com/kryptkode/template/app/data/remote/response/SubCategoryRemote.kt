package com.kryptkode.template.app.data.remote.response

import com.squareup.moshi.Json

/**
 * Created by kryptkode on 2/19/2020.
 */
data class SubCategoryRemote(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "parent")
    val parentId: String,
    @Json(name = "icon")
    val imageUrl: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "order_by")
    val sort: String
)
package com.kryptkode.template.app.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by kryptkode on 2/19/2020.
 */
@JsonClass(generateAdapter = true)
data class CategoryRemote(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "icon")
    val imageUrl: String,
    @field:Json(name = "order_by")
    val sortOrder: String,
    @field:Json(name = "status")
    val status: String
)
package com.kryptkode.template.app.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by kryptkode on 2/19/2020.
 */
@JsonClass(generateAdapter = true)
data class LinkRemote(
    @field:Json(name="link")
    val url: String
)
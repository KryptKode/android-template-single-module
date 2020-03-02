package com.kryptkode.template.app.data.remote.response

import com.squareup.moshi.Json

/**
 * Created by kryptkode on 2/19/2020.
 */
data class LinkRemote(
    @Json(name="link")
    val url: String
)
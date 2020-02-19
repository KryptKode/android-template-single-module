package com.kryptkode.template.app.data.remote.endpoint

/**
 * Created by kryptkode on 12/27/2019.
 */
enum class Endpoint(
    val url: String,
    val description: String
) {
    UNKNOWN("", "An unknown path");

    companion object {
        fun fromPath(path: String?): Endpoint {
            return when (path) {
                else -> UNKNOWN
            }
        }
    }

}
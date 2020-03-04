package com.kryptkode.template.app.utils

import com.kryptkode.template.BuildConfig

/**
 * Created by kryptkode on 3/4/2020.
 */
object ImageUrl {
    fun getImageUrl(path:String?): String {
        return "${BuildConfig.IMAGE_URL}$path"
    }
}
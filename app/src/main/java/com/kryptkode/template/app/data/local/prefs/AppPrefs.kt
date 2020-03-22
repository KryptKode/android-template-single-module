package com.kryptkode.template.app.data.local.prefs


/**
 * Created by kryptkode on 10/23/2019.
 */

interface AppPrefs {
    fun setLink(url: String)
    fun getLink(): String

    fun setLinkCacheTime(time: Long)
    fun getLinkCacheTime(): Long

    fun setCardCacheTime(subcategoryId: String, time: Long)
    fun setCategoryCacheTime(time: Long)
    fun getCardCacheTime(subcategoryId: String): Long
    fun getCategoryCacheTime(): Long

    fun isCategoryLocked(categoryId:String): Boolean
    fun setCategoryLocked(categoryId: String, value: Boolean)

    fun setDateWhenCategoryWasUnlocked(categoryId: String, time: Long)
    fun getDateWhenCategoryWasUnlocked(categoryId: String):Long
}
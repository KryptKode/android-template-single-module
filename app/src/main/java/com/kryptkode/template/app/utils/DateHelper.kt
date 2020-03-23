package com.kryptkode.template.app.utils

import android.content.Context
import androidx.annotation.IntegerRes
import com.kryptkode.template.R
import java.util.*

/**
 * Created by kryptkode on 3/8/2020.
 */
class DateHelper(private val context: Context) {

    fun now(): Date {
        return Date()
    }

    fun nowInMillis(): Long {
        return now().time
    }

    fun hasCardCacheExpired(timeInMillis: Long): Boolean {
        return hasTimeExpired(timeInMillis, getCardCacheTime())
    }

    fun hasCategoryCacheExpired(timeInMillis: Long): Boolean {
        return hasTimeExpired(timeInMillis, getCategoryCacheTime())
    }

    fun hasLinkCacheExpired(timeInMillis: Long): Boolean {
        return hasTimeExpired(timeInMillis, getLinkCacheTime())
    }

    private fun hasTimeExpired(timeInMillis: Long, timeLimit: Long): Boolean {
        return nowInMillis() - timeInMillis >= timeLimit
    }

    private fun getCardCacheTime(): Long {
        return getCacheTime(R.integer.card_cache_time_in_hours)
    }

    private fun getCategoryCacheTime(): Long {
        return getCacheTime(R.integer.category_cache_time_in_hours)
    }

    private fun getLinkCacheTime(): Long {
        return getCacheTime(R.integer.link_cache_time_in_hours)
    }

    private fun getCacheTime(@IntegerRes resId: Int): Long {
        var timeInt = context.resources.getInteger(resId)
        if (timeInt <= 0) {
            timeInt = 1
        }
        return timeInt * Constants.ONE_HOUR_IN_MILLIS
    }

    private fun getLockedTime(@IntegerRes resId: Int): Long {
        var timeInt = context.resources.getInteger(resId)
        if (timeInt <= 0) {
            timeInt = 1
        }
        return timeInt * Constants.ONE_DAY_IN_MILLIS
    }

    fun hasDateWhenCategoryWasUnlockedBeenLongEnough(timeInMillis: Long): Boolean {
        return nowInMillis() - timeInMillis >= getLockedTime(R.integer.category_lock_days)
    }
}
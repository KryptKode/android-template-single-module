package com.kryptkode.template.app.data.local.prefs

import android.content.SharedPreferences

/**
 * Created by kryptkode on 10/23/2019.
 */

abstract class BasePreferencesManager(private val sharedPreferences: SharedPreferences) {

    protected val prefLink = "prefLink"
    protected val prefLinkCacheTime = "prefLinkCacheTime"
    protected val prefCardCacheTime = "prefCardCacheTime"
    protected val prefCategoryCacheTime = "prefCategoryCacheTime"
    protected val prefCategoryLocked = "prefCategoryLocked"
    protected val prefDateCategoryUnLocked = "prefDateCategoryUnLocked"
    protected val defaultStringValue = "{}"

    protected val PREF_KEY_INSTALL_DATE = "android_rate_install_date"

    protected val PREF_KEY_LAUNCH_TIMES = "android_rate_launch_times"

    protected val PREF_KEY_IS_AGREE_SHOW_DIALOG = "android_rate_is_agree_show_dialog"

    protected val PREF_KEY_REMIND_INTERVAL = "android_rate_remind_interval"

    protected fun setStringPreference(key: String, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    protected fun getStringPreference(
        key: String,
        defaultValue: String = defaultStringValue
    ): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultStringValue
    }

    protected fun setIntPreference(key: String, value: Int) {
        sharedPreferences.edit()
            .putInt(key, value)
            .apply()
    }

    protected fun getIntPreference(key: String, defaultValue: Int=-1): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }


    protected fun setLongPreference(key: String, value: Long) {
        sharedPreferences.edit()
            .putLong(key, value)
            .apply()
    }

    protected fun getLongPreference(key: String, defaultValue: Long = -1): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    protected fun getBooleanPreference(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    protected fun setBooleanPreference(key: String, value: Boolean) {
        sharedPreferences.edit()
            .putBoolean(key, value)
            .apply()
    }

    fun resetPreferences(): Boolean {
        sharedPreferences.edit().clear().apply()
        return true
    }

    fun removePreference(key: String) {
        sharedPreferences.edit()
            .remove(key)
            .apply()
    }
}
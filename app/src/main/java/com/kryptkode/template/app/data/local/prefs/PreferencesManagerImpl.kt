package com.kryptkode.template.app.data.local.prefs

import android.content.SharedPreferences

/**
 * Created by kryptkode on 10/23/2019.
 */

open class PreferencesManagerImpl (
    sharedPreferences: SharedPreferences
) : BasePreferencesManager(sharedPreferences), AppPrefs {

    override fun setLink(url: String) {
        return setStringPreference(prefLink, url)
    }

    override fun getLink(): String {
        return getStringPreference(prefLink, "")
    }

    override fun setLinkCacheTime(time: Long) {
        return setLongPreference(prefLinkCacheTime, time)
    }

    override fun getLinkCacheTime(): Long {
        return getLongPreference(prefLinkCacheTime)
    }

    override fun setCardCacheTime(time: Long) {
        return setLongPreference(prefCardCacheTime, time)
    }

    override fun getCardCacheTime(): Long {
        return getLongPreference(prefCardCacheTime)
    }
}
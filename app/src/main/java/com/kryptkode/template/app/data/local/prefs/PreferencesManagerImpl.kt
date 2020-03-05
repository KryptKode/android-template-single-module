package com.kryptkode.template.app.data.local.prefs

import android.content.SharedPreferences
import com.kryptkode.template.app.utils.rating.RatingDataProvider
import java.util.*

/**
 * Created by kryptkode on 10/23/2019.
 */

open class PreferencesManagerImpl (
    sharedPreferences: SharedPreferences
) : BasePreferencesManager(sharedPreferences), AppPrefs, RatingDataProvider {

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

    override fun setAgreeShowDialog(isAgree: Boolean) {
        setBooleanPreference(PREF_KEY_IS_AGREE_SHOW_DIALOG, isAgree)
    }

    override fun getIsAgreeShowDialog(): Boolean {
        return getBooleanPreference(PREF_KEY_IS_AGREE_SHOW_DIALOG, true)
    }

    override fun setRemindInterval() {
        removePreference(PREF_KEY_REMIND_INTERVAL)
        setLongPreference(PREF_KEY_REMIND_INTERVAL, Date().time)
    }

    override fun getRemindInterval(): Long {
        return getLongPreference(PREF_KEY_REMIND_INTERVAL)
    }

    override fun setInstallDate() {
        return setLongPreference(PREF_KEY_INSTALL_DATE, Date().time)
    }

    override fun getInstallDate(): Long {
        return getLongPreference(PREF_KEY_INSTALL_DATE)
    }

    override fun setLaunchTimes(launchTimes: Int) {
        return setIntPreference(PREF_KEY_LAUNCH_TIMES, launchTimes)
    }

    override fun getLaunchTimes(): Int {
        return getIntPreference(PREF_KEY_LAUNCH_TIMES)
    }

    override fun isFirstLaunch(): Boolean {
        return getLongPreference(PREF_KEY_INSTALL_DATE) == 0L
    }
}
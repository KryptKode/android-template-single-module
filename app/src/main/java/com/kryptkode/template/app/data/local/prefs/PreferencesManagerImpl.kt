package com.kryptkode.template.app.data.local.prefs

import android.content.SharedPreferences
import com.kryptkode.template.app.utils.rating.RatingDataProvider
import java.util.*

/**
 * Created by kryptkode on 10/23/2019.
 */

open class PreferencesManagerImpl(
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


    override fun setCardCacheTime(subcategoryId: String, time: Long) {
        return setLongPreference(getCardKey(subcategoryId), time)
    }

    private fun getCardKey(subcategoryId: String): String {
        return "$prefCardCacheTime-$subcategoryId"
    }

    override fun getCardCacheTime(subcategoryId: String): Long {
        return getLongPreference(getCardKey(subcategoryId))
    }

    override fun getCategoryCacheTime(): Long {
        return getLongPreference(prefCategoryCacheTime)
    }

    override fun setCategoryCacheTime(time: Long) {
        return setLongPreference(prefCategoryCacheTime, time)
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
        return getLongPreference(PREF_KEY_INSTALL_DATE) == -1L
    }

    override fun isCategoryLocked(categoryId:String, lockedByDefault:Boolean): Boolean {
        return getBooleanPreference(getCategoryPositionKey(categoryId), lockedByDefault)
    }

    override fun setCategoryLocked(categoryId: String, value: Boolean) {
        return setBooleanPreference(getCategoryPositionKey(categoryId), value)
    }

    private fun getCategoryPositionKey(categoryId: String): String {
        return "$prefCategoryLocked-$categoryId"
    }

    override fun getDateWhenCategoryWasUnlocked(categoryId: String): Long {
        return getLongPreference(getDateCategoryUnlockedKey(categoryId))
    }

    override fun setDateWhenCategoryWasUnlocked(categoryId: String, time: Long) {
        return setLongPreference(getDateCategoryUnlockedKey(categoryId), time)
    }

    private fun getDateCategoryUnlockedKey(categoryId: String): String {
        return "$prefDateCategoryUnLocked-$categoryId"
    }
}
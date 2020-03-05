package com.kryptkode.template.app.utils.rating

import java.util.*



class RatingManager (private val prefsHelper: RatingDataProvider) {

    private var installDate = 10

    private var launchTimes = 10

    private var remindInterval = 1

    private var isDebug = false

    fun showRateIfMeetsConditions(): Boolean {
        return isDebug || shouldShowRateDialog()
    }

    fun monitor() {
        if (prefsHelper.isFirstLaunch()) {
            prefsHelper.setInstallDate()
        }
        prefsHelper.setLaunchTimes(prefsHelper.getLaunchTimes() + 1)

    }

    fun setLaunchTimes(launchTimes: Int): RatingManager {
        this.launchTimes = launchTimes
        return this
    }

    fun setInstallDays(installDate: Int): RatingManager {
        this.installDate = installDate
        return this
    }

    fun setRemindInterval(remindInterval: Int): RatingManager {
        this.remindInterval = remindInterval
        return this
    }

    fun setAgreeShowDialog(agreed:Boolean): RatingManager {
        prefsHelper.setAgreeShowDialog(agreed)
        return this
    }


   private fun shouldShowRateDialog(): Boolean {
        return prefsHelper.getIsAgreeShowDialog() &&
                isOverLaunchTimes() &&
                isOverInstallDate() &&
                isOverRemindDate()
    }

    private fun isOverLaunchTimes(): Boolean {
        return prefsHelper.getLaunchTimes() >= launchTimes
    }

    private fun isOverInstallDate(): Boolean {
        return isOverDate(prefsHelper.getInstallDate(), installDate)
    }

    private fun isOverRemindDate(): Boolean {
        return isOverDate(prefsHelper.getRemindInterval(), remindInterval)
    }

    private fun isOverDate(targetDate: Long, threshold: Int): Boolean {
        return Date().getTime() - targetDate >= threshold * 24 * 60 * 60 * 1000
    }

    fun isDebug(): Boolean {
        return isDebug
    }

    fun setDebug(isDebug: Boolean): RatingManager {
        this.isDebug = isDebug
        return this
    }
}
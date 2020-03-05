package com.kryptkode.template.app.utils.rating

/**
 * Created by kryptkode on 2/11/2020.
 */
interface RatingDataProvider {
    fun setAgreeShowDialog(isAgree: Boolean)
    fun getIsAgreeShowDialog(): Boolean
    fun setRemindInterval()
    fun getRemindInterval(): Long
    fun setInstallDate()
    fun getInstallDate(): Long
    fun setLaunchTimes(launchTimes: Int)
    fun getLaunchTimes(): Int
    fun isFirstLaunch(): Boolean
}
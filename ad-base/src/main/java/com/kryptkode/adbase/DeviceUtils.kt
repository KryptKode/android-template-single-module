package com.kryptkode.adbase

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object DeviceUtils {
    @SuppressLint("HardwareIds")
    fun getDeviceID(context: Context): String {
        return md5Hash(
            Settings.Secure.getString(
                context.contentResolver,
                "android_id"
            )
        ).toUpperCase()
    }

    private fun md5Hash(str: String): String {
        var instance: MessageDigest?
        try {
            instance = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            instance = null
        }

        instance?.update(str.toByteArray(), 0, str.length)
        return BigInteger(1, instance?.digest()).toString(16)
    }
}
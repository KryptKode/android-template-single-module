package com.kryptkode.template.app.utils.sharing

import android.content.Context
import android.content.Intent
import com.kryptkode.template.R
import com.kryptkode.template.app.utils.rating.UriHelper

/**
 * Created by kryptkode on 2/11/2020.
 */
class ShareUtils(private val context: Context) {

    companion object {
        const val PLAY_STORE_URL_BASE = "https://play.google.com/store/apps/details?id="
        const val IMAGE_MIME_TYPE = "image/*"
        const val TEXT_MIME_TYPE = "text/*"
    }


    fun getPlayStoreUrl(): String {
        return "$PLAY_STORE_URL_BASE${context.packageName}"
    }

    fun createShareAppIntent(): Intent? {
        val intent = Intent(Intent.ACTION_SEND)
        val text = context.getString(R.string.share_text, getPlayStoreUrl())
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.type =
            TEXT_MIME_TYPE
        return Intent.createChooser(intent, context.getString(R.string.share_msg))
    }

    private val GOOGLE_PLAY_PACKAGE_NAME = "com.android.vending"
    fun createRateIntentForGooglePlay(): Intent {
        val packageName = context.packageName
        val intent = Intent(Intent.ACTION_VIEW, UriHelper.getGooglePlay(packageName))
        if (UriHelper.isPackageExists(context, GOOGLE_PLAY_PACKAGE_NAME)) {
            intent.setPackage(GOOGLE_PLAY_PACKAGE_NAME)
        }
        return intent
    }

    fun createRateIntentForAmazonAppstore(): Intent {
        val packageName = context.packageName
        return Intent(Intent.ACTION_VIEW, UriHelper.getAmazonAppstore(packageName))
    }

}
package com.kryptkode.template.app.utils.sharing

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.kryptkode.template.R
import com.kryptkode.template.app.utils.rating.UriHelper
import timber.log.Timber
import java.io.File

/**
 * Created by kryptkode on 2/11/2020.
 */
class ShareUtils(private val context: Context) {

    companion object {
        const val PLAY_STORE_URL_BASE = "https://play.google.com/store/apps/details?id="
        const val IMAGE_MIME_TYPE = "image/*"
        const val TEXT_MIME_TYPE = "text/*"
        const val PROVIDER = ".provider"
        const val WHATS_APP_PACKAGE_NAME = "com.whatsapp"
        const val FACEBOOK_PACKAGE_NAME = "com.facebook.katana"
        const val TWITTER_PACKAGE_NAME = "com.twitter.android"
        const val GOOGLE_PLAY_PACKAGE_NAME = "com.android.vending"
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

    var listener: ShareListener? = null

    private fun getPlayStoreUrl(): String {
        return "${PlayStoreUtils.PLAY_STORE_URL_BASE}${context.packageName}"
    }



    private fun getUriForFile(file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}$PROVIDER",
            file
        )
    }

    fun shareFileOthers(file: File){
        shareOthers(getUriForFile(file))
    }

    fun shareOthers(uri: Uri) {
        val intent = Intent(Intent.ACTION_SEND)
        val text = getShareText()
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.type = IMAGE_MIME_TYPE
        context.startActivity(
            Intent.createChooser(
                intent,
                context.getString(R.string.share_with)
            )
        )
    }

    fun shareFileByPackageName(uri: Uri, packageName: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            val text = getShareText()
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.putExtra(Intent.EXTRA_TEXT, text)
            intent.type = IMAGE_MIME_TYPE
            intent.setPackage(packageName)
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Timber.e(e)
            listener?.onShareError(errorMessageFromPackageName(packageName))
        }
    }

    private fun getShareText() =
        context.getString(
            R.string.share_image_text,
            context.getString(R.string.app_name),
            getPlayStoreUrl()
        )

    fun shareViaWhatsApp(file: File){
        shareFileByPackageName(getUriForFile(file), WHATS_APP_PACKAGE_NAME)
    }


    fun shareViaFacebook(file: File){
        shareFileByPackageName(getUriForFile(file), FACEBOOK_PACKAGE_NAME)
    }

    fun shareViaTwitter(file: File){
        shareFileByPackageName(getUriForFile(file), TWITTER_PACKAGE_NAME)
    }


    private fun errorMessageFromPackageName(packageName: String): String {
        return when (packageName) {
            WHATS_APP_PACKAGE_NAME -> context.getString(R.string.whats_app_not_installed_error_message)
            FACEBOOK_PACKAGE_NAME -> context.getString(R.string.facebook_not_installed_error_message)
            TWITTER_PACKAGE_NAME -> context.getString(R.string.twitter_not_installed_error_message)
            else -> context.getString(R.string.other_share_error_message)
        }
    }
    
    interface ShareListener {
        fun onShareError(message: String)
    }


}
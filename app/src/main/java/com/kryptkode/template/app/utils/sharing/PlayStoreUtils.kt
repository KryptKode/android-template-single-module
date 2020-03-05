package com.kryptkode.template.app.utils.sharing

import android.content.ComponentName
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.kryptkode.template.R


class PlayStoreUtils(private val context: FragmentActivity) {

    fun openPlayStore(appPackageName: String = getAppPackageName()) {
        val marketIntent = createPlayStoreMarketIntent(appPackageName)
        val playStoreInfo = resolvePlayStore(marketIntent)
        if (playStoreInfo != null) {
            val component = getComponent(playStoreInfo)
            addComponentAndFlagsToIntent(marketIntent, component)
            openPlayStoreDirectly(marketIntent)
        } else {
            openPlayStoreInBrowser(appPackageName)
        }
    }

    fun openPlayStoreForDeveloper(developerId: String = DEVELOPER_ID) {
        /*  val marketIntent = createPlayStoreDevMarketIntent(developerId)
          val playStoreInfo = resolvePlayStore(marketIntent)
          if (playStoreInfo != null) {
              val component = getComponent(playStoreInfo)
              addComponentAndFlagsToIntent(marketIntent, component)
              openPlayStoreDirectly(marketIntent)
          } else {
              openPlayStoreDevInBrowser(developerId)
          }*/
        openPlayStoreDevInBrowser(developerId)
    }

    private fun openPlayStoreDirectly(intent: Intent) {
        context.startActivity(intent)
    }

    private fun openPlayStoreInBrowser(appPackageName: String) {
        val intent = createPlayStoreBrowserIntent(appPackageName)
        context.startActivity(
            Intent.createChooser(
                intent,
                context.getString(R.string.open_playstore_with)
            )
        )
    }

    private fun openPlayStoreDevInBrowser(developerId: String) {
        val intent = createPlayStoreDevBrowserIntent(developerId)
        context.startActivity(
            Intent.createChooser(
                intent,
                context.getString(R.string.open_playstore_with)
            )
        )
    }


    private fun createPlayStoreMarketIntent(appPackageName: String): Intent {
        val marketUrl = getMarketUrl(appPackageName)
        val marketUri = convertToUri(marketUrl)
        return Intent(Intent.ACTION_VIEW, marketUri)
    }

    private fun createPlayStoreDevMarketIntent(appPackageName: String): Intent {
        val marketUrl = getMarketUrl(appPackageName)
        val marketUri = convertToUri(marketUrl)
        return Intent(Intent.ACTION_VIEW, marketUri)
    }


    private fun addComponentAndFlagsToIntent(intent: Intent, componentName: ComponentName) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.component = componentName
    }

    private fun createPlayStoreDevBrowserIntent(developerId: String): Intent {
        val playStoreUrl = getPlayStoreDevUrl(developerId)
        val playStoreBrowserUri = convertToUri(playStoreUrl)
        return Intent(Intent.ACTION_VIEW, playStoreBrowserUri)
    }

    private fun createPlayStoreBrowserIntent(appPackageName: String): Intent {
        val playStoreUrl = getPlayStoreUrl(appPackageName)
        val playStoreBrowserUri = convertToUri(playStoreUrl)
        return Intent(Intent.ACTION_VIEW, playStoreBrowserUri)
    }

    private fun resolvePlayStore(intent: Intent): ActivityInfo? {
        val otherAppsResolveInfo = context.packageManager
            .queryIntentActivities(intent, 0)
        for (resolveInfo in otherAppsResolveInfo) {
            if (resolveInfo.activityInfo.applicationInfo.packageName == PLAY_STORE_PACKAGE_NAME) {
                return resolveInfo.activityInfo
            }
        }
        return null
    }

    private fun getComponent(activityInfo: ActivityInfo): ComponentName {
        return ComponentName(
            activityInfo.applicationInfo.packageName,
            activityInfo.name
        )
    }

    private fun convertToUri(uri: String): Uri? {
        return Uri.parse(uri)
    }

    private fun getAppPackageName(): String {
        return context.packageName
    }

    private fun getMarketUrl(appPackageName: String): String {
        return "$MARKET_URI_BASE$appPackageName"
    }

    private fun getPlayStoreUrl(appPackageName: String): String {
        return "$PLAY_STORE_URL_BASE$appPackageName"
    }

    private fun getMarketDevUrl(developerId: String): String {
        return "$MARKET_DEV_URI_BASE$developerId"
    }

    private fun getPlayStoreDevUrl(developerId: String): String {
        return "$PLAY_STORE__DEV_URL_BASE$developerId"
    }

    companion object {
        const val PLAY_STORE_URL_BASE = "https://play.google.com/store/apps/details?id="
        const val PLAY_STORE__DEV_URL_BASE = "https://play.google.com/store/apps/developer?id="
        const val MARKET_URI_BASE = "market://details?id="
        const val MARKET_DEV_URI_BASE = "market://dev?id="
        const val PLAY_STORE_PACKAGE_NAME = "com.android.vending"
        const val DEVELOPER_ID = "Handikapp"
    }
}
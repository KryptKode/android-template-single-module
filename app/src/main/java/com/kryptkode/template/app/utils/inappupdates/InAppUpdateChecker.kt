package com.kryptkode.template.app.utils.inappupdates

import android.app.Activity
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.kryptkode.template.R
import com.kryptkode.template.app.utils.AppToastCreator
import timber.log.Timber

/**
 * Created by kryptkode on 4/25/2020.
 */
class InAppUpdateChecker(
    private val appToastCreator: AppToastCreator,
    private val activity: AppCompatActivity
) {

    private val handler = object : InAppUpdateManager.InAppUpdateHandler {
        override fun onInAppUpdateError(code: Int, error: Throwable?) {
            Timber.e("In app update failed with error: $code, $error")
            appToastCreator.showToast(getString(R.string.remote_update_failed_msg))
        }

        override fun onInAppUpdateStatus(status: InAppUpdateStatus?) {
            Timber.d("In-app update status: $status")
        }
    }

    private val inAppUpdateManager =
        InAppUpdateManager.Builder(activity, REQ_CODE_VERSION_UPDATE)
            .resumeUpdates(true) // Resume the update, if the update was stalled. Default is true
            .mode(InAppUpdateConstants.UpdateMode.FLEXIBLE)
            .snackBarMessage("An update has just been downloaded.")
            .snackBarAction("RESTART")
            .handler(handler)


    fun checkForUpdates() {
        inAppUpdateManager.checkForAppUpdate()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode != Activity.RESULT_OK) {
                // If the update is cancelled or fails,
                // you can request to start the update again.
                Timber.e("Update flow failed! Result code: $resultCode")
                appToastCreator.showToast(getString(R.string.remote_update_failed_msg))
            }
        }
    }

    private fun getString(@StringRes resId: Int): String {
        return activity.getString(resId)
    }

    companion object {
        private const val REQ_CODE_VERSION_UPDATE = 1212
    }
}
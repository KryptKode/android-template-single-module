package com.kryptkode.template.app.data.local.prefs

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Created by kryptkode on 10/23/2019.
 */

open class PreferencesManagerImpl @Inject constructor(
    sharedPreferences: SharedPreferences
) : BasePreferencesManager(sharedPreferences), AppPrefs {

}
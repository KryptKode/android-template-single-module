package com.kryptkode.template.app.data.local.prefs

import android.content.SharedPreferences
import com.afollestad.rxkprefs.rxjava.observe
import com.afollestad.rxkprefs.rxkPrefs
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by kryptkode on 10/23/2019.
 */

abstract class BasePreferencesManager(protected val sharedPreferences: SharedPreferences) {

    protected val defaultStringValue = "{}"
    private val rxkPrefs = rxkPrefs(sharedPreferences)

    protected fun setStringPreference(key: String, value: String): Completable {
        return Completable.fromCallable {
            rxkPrefs.string(key).set(value)
        }
    }

    protected fun getStringPreference(
        key: String,
        defaultValue: String = defaultStringValue
    ): Observable<String> {
        return rxkPrefs.string(key, defaultValue).observe()
    }

    protected fun setIntPreference(key: String, value: Int): Completable {
        return Completable.fromCallable {
            rxkPrefs.integer(key).set(value)
        }
    }

    protected fun getIntPreference(key: String, defaultValue: Int = -1): Observable<Int> {
        return rxkPrefs.integer(key, defaultValue).observe()
    }


    protected fun setLongPreference(key: String, value: Long): Completable {
        return Completable.fromCallable {
            rxkPrefs.long(key).set(value)
        }
    }

    protected fun getLongPreference(key: String, defaultValue: Long = -1L): Observable<Long> {
        return rxkPrefs.long(key, defaultValue).observe()
    }

    protected fun getBooleanPreference(
        key: String,
        defaultValue: Boolean = false
    ): Observable<Boolean> {
        return rxkPrefs.boolean(key, defaultValue).observe()
    }

    protected fun setBooleanPreference(key: String, value: Boolean): Completable {
        return Completable.fromCallable {
            rxkPrefs.boolean(key).set(value)
        }
    }
}
package com.kryptkode.template.app.di.application

import android.content.Context
import android.content.SharedPreferences
import com.kryptkode.template.BuildConfig
import com.kryptkode.template.app.data.dispatchers.AppDispatchers
import com.kryptkode.template.app.data.local.prefs.AppPrefs
import com.kryptkode.template.app.data.local.prefs.PreferencesManagerImpl
import com.kryptkode.template.app.data.local.room.AppDb
import com.kryptkode.template.app.data.schedulers.AppSchedulers
import com.kryptkode.template.app.utils.Constants.APP_PREFS
import com.securepreferences.SecurePreferences
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers

/**
 * Created by kryptkode on 2/19/2020.
 */

@Module
class AppModule {


    @ApplicationScope
    @Provides
    fun providePrefs(sharedPreferences: SharedPreferences): AppPrefs {
        return PreferencesManagerImpl(sharedPreferences)
    }

    @Provides
    @ApplicationScope
    fun provideSharedPrefs(context: Context): SharedPreferences {
        return if (BuildConfig.DEBUG) context.getSharedPreferences(
            APP_PREFS,
            Context.MODE_PRIVATE
        ) else SecurePreferences(context, APP_PREFS, APP_PREFS)
    }

    @ApplicationScope
    @Provides
    fun provideAppDb(context: Context): AppDb {
        return AppDb.getInstance(context)
    }

    @ApplicationScope
    @Provides
    fun provideAppDispatchers(): AppDispatchers {
        return AppDispatchers(Dispatchers.IO, Dispatchers.Default, Dispatchers.Main)
    }


    @ApplicationScope
    @Provides
    fun provideAppSchedulers(): AppSchedulers {
        return AppSchedulers(
            Schedulers.computation(),
            Schedulers.io(),
            AndroidSchedulers.mainThread()
        )
    }
}


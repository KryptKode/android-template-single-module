package com.kryptkode.template.app.di.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.kryptkode.template.BuildConfig
import com.kryptkode.template.app.App
import com.kryptkode.template.app.data.dispatchers.AppDispatchers
import com.kryptkode.template.app.data.local.prefs.AppPrefs
import com.kryptkode.template.app.data.local.prefs.PreferencesManagerImpl
import com.kryptkode.template.app.data.local.room.AppDb
import com.kryptkode.template.app.data.remote.api.Api
import com.kryptkode.template.app.data.remote.api.RestClient
import com.kryptkode.template.app.utils.Constants.APP_PREFS
import com.securepreferences.SecurePreferences
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

/**
 * Created by kryptkode on 2/19/2020.
 */

@Module
class AppModule {


    @ApplicationScope
    @Provides
    fun provideApplication(app: App): Application {
        return app
    }


    @ApplicationScope
    @Provides
    fun providePrefs(sharedPreferences: SharedPreferences): PreferencesManagerImpl {
        return PreferencesManagerImpl(sharedPreferences)
    }

    @ApplicationScope
    @Provides
    fun provideAppPrefs(preferencesManagerImpl: PreferencesManagerImpl): AppPrefs {
        return preferencesManagerImpl
    }

    @Provides
    @ApplicationScope
    fun provideSharedPrefs(context: Application): SharedPreferences {
        return if (BuildConfig.DEBUG) context.getSharedPreferences(
            APP_PREFS,
            Context.MODE_PRIVATE
        ) else SecurePreferences(context, APP_PREFS, APP_PREFS)
    }

    @ApplicationScope
    @Provides
    fun provideAppDb(context: Application): AppDb {
        return AppDb.getInstance(context)
    }

    @ApplicationScope
    @Provides
    fun provideAppDispatchers(): AppDispatchers {
        return AppDispatchers(Dispatchers.IO, Dispatchers.Default, Dispatchers.Main)
    }

    @ApplicationScope
    @Provides
    fun provideRestClient(context: Application): RestClient {
        return RestClient(context)
    }

    @ApplicationScope
    @Provides
    fun provideApi(restClient: RestClient): Api {
        return restClient.getRemote()
    }
}


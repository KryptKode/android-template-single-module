package com.kryptkode.template.app.di.screen.modules

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.kryptkode.template.app.data.local.Local
import com.kryptkode.template.app.data.local.LocalImpl
import com.kryptkode.template.app.data.local.mapper.LocalMappers
import com.kryptkode.template.app.data.local.prefs.AppPrefs
import com.kryptkode.template.app.data.local.room.AppDb
import com.kryptkode.template.app.data.remote.Remote
import com.kryptkode.template.app.data.remote.RemoteImpl
import com.kryptkode.template.app.data.remote.api.Api
import com.kryptkode.template.app.data.remote.mapper.RemoteMappers
import com.kryptkode.template.app.di.screen.ScreenScope
import com.kryptkode.template.app.di.screen.modules.mapper.MapperModule
import com.kryptkode.template.app.di.screen.modules.mapper.local.LocalMapperModule
import com.kryptkode.template.app.di.screen.modules.repo.RepositoryModule
import dagger.Module
import dagger.Provides

/**
 * Created by kryptkode on 2/19/2020.
 */
@Module(includes = [LocalMapperModule::class, RepositoryModule::class, MapperModule::class])
class ScreenModule(private val activity: FragmentActivity) {

    @Provides
    @ScreenScope
    fun provideContext(): Context {
        return activity
    }

    @Provides
    @ScreenScope
    fun provideActivity(): FragmentActivity {
        return activity
    }

    @Provides
    @ScreenScope
    fun provideLocal(appDb: AppDb, appPrefs: AppPrefs, localMappers: LocalMappers): Local {
        return LocalImpl(appDb, appPrefs, localMappers)
    }

    @Provides
    @ScreenScope
    fun provideRemote(api: Api, remoteMappers: RemoteMappers): Remote {
        return RemoteImpl(api, remoteMappers)
    }
}
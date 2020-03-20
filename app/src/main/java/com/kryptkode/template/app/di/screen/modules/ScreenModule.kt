package com.kryptkode.template.app.di.screen.modules

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.kryptkode.template.Navigator
import com.kryptkode.template.app.data.domain.error.ErrorHandler
import com.kryptkode.template.app.data.local.Local
import com.kryptkode.template.app.data.local.LocalImpl
import com.kryptkode.template.app.data.local.mapper.LocalMappers
import com.kryptkode.template.app.data.local.prefs.AppPrefs
import com.kryptkode.template.app.data.local.prefs.PreferencesManagerImpl
import com.kryptkode.template.app.data.local.room.AppDb
import com.kryptkode.template.app.data.remote.Remote
import com.kryptkode.template.app.data.remote.RemoteImpl
import com.kryptkode.template.app.data.remote.api.Api
import com.kryptkode.template.app.data.remote.mapper.RemoteMappers
import com.kryptkode.template.app.data.repo.ErrorHandlerImpl
import com.kryptkode.template.app.di.screen.ScreenScope
import com.kryptkode.template.app.di.screen.modules.mapper.MapperModule
import com.kryptkode.template.app.di.screen.modules.mapper.local.LocalMapperModule
import com.kryptkode.template.app.di.screen.modules.repo.RepositoryModule
import com.kryptkode.template.app.utils.AppToastCreator
import com.kryptkode.template.app.utils.DateHelper
import com.kryptkode.template.app.utils.rating.RatingDataProvider
import com.kryptkode.template.app.utils.rating.RatingManager
import com.kryptkode.template.app.utils.sharing.PlayStoreUtils
import com.kryptkode.template.app.utils.sharing.ShareUtils
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
    fun provideLocal(
        appDb: AppDb,
        appPrefs: AppPrefs,
        localMappers: LocalMappers,
        dateHelper: DateHelper
    ): Local {
        return LocalImpl(appDb, appPrefs, localMappers, dateHelper)
    }

    @Provides
    @ScreenScope
    fun provideRemote(api: Api, remoteMappers: RemoteMappers): Remote {
        return RemoteImpl(api, remoteMappers)
    }

    @Provides
    @ScreenScope
    fun provideShareUtils(@ScreenScope context: Context): ShareUtils {
        return ShareUtils(context)
    }

    @Provides
    @ScreenScope
    fun provideAppPrefs(preferencesManagerImpl: PreferencesManagerImpl): RatingDataProvider {
        return preferencesManagerImpl
    }

    @Provides
    @ScreenScope
    fun provideRatingManager(ratingDataProvider: RatingDataProvider): RatingManager {
        return RatingManager(ratingDataProvider)
    }

    @Provides
    @ScreenScope
    fun provideNavigator(activity: FragmentActivity): Navigator {
        return Navigator(activity)
    }

    @Provides
    @ScreenScope
    fun providePlayStoreUtils(activity: FragmentActivity): PlayStoreUtils {
        return PlayStoreUtils(activity)
    }

    @Provides
    @ScreenScope
    fun provideAppToastCreator(context: Context): AppToastCreator {
        return AppToastCreator(context)
    }

    @Provides
    @ScreenScope
    fun provideErrorHandler(context: Context): ErrorHandler{
        return ErrorHandlerImpl(context)
    }
}
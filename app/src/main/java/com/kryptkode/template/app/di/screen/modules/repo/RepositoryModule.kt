package com.kryptkode.template.app.di.screen.modules.repo

import com.kryptkode.template.app.data.dispatchers.AppDispatchers
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.app.data.local.Local
import com.kryptkode.template.app.data.remote.Remote
import com.kryptkode.template.app.data.repo.CategoryRepositoryImpl
import com.kryptkode.template.app.di.screen.ScreenScope
import dagger.Module
import dagger.Provides

/**
 * Created by kryptkode on 3/2/2020.
 */

@Module
class RepositoryModule {
    @Provides
    @ScreenScope
    fun provideCategoryRepository(
        appDispatchers: AppDispatchers,
        remote: Remote,
        local: Local
    ): CategoryRepository {
        return CategoryRepositoryImpl(appDispatchers, local, remote)
    }
}
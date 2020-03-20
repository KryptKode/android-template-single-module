package com.kryptkode.template.app.di.screen.modules.repo

import com.kryptkode.template.app.data.dispatchers.AppDispatchers
import com.kryptkode.template.app.data.domain.error.ErrorHandler
import com.kryptkode.template.app.data.domain.repository.CardRepository
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.app.data.domain.repository.SubCategoryRepository
import com.kryptkode.template.app.data.local.Local
import com.kryptkode.template.app.data.remote.Remote
import com.kryptkode.template.app.data.repo.CardRepositoryImpl
import com.kryptkode.template.app.data.repo.CategoryRepositoryImpl
import com.kryptkode.template.app.data.repo.SubcategoryRepositoryImpl
import com.kryptkode.template.app.di.screen.ScreenScope
import com.kryptkode.template.app.utils.DateHelper
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
        dateHelper: DateHelper,
        local: Local,
        errorHandler: ErrorHandler
    ): CategoryRepository {
        return CategoryRepositoryImpl(appDispatchers, dateHelper, local, remote, errorHandler)
    }

    @Provides
    @ScreenScope
    fun provideSubCategoryRepository(
        local: Local,
        errorHandler: ErrorHandler
    ): SubCategoryRepository {
        return SubcategoryRepositoryImpl(local,errorHandler)
    }

    @Provides
    @ScreenScope
    fun provideCardRepository(
        appDispatchers: AppDispatchers,
        remote: Remote,
        dateHelper: DateHelper,
        local: Local,
        errorHandler: ErrorHandler
    ): CardRepository {
        return CardRepositoryImpl(appDispatchers, dateHelper, local, remote, errorHandler)
    }
}
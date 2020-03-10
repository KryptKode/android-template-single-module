package com.kryptkode.template.app.di.screen.viewmodel

import com.kryptkode.template.MainActivityViewModel
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.categories.CategoriesViewModel
import com.kryptkode.template.categories.mapper.CategoryViewMapper
import com.kryptkode.template.startnav.StartNavViewModel
import com.kryptkode.template.startnav.mapper.CategoryWithSubcategoriesViewMapper
import dagger.Module
import dagger.Provides

/**
 * Created by kryptkode on 3/2/2020.
 */
@Module
class ViewModelProviderModule {

    @Provides
    fun provideCategoriesViewModel(
        categoryRepository: CategoryRepository,
        categoryViewMapper: CategoryViewMapper
    ): CategoriesViewModel {
        return CategoriesViewModel(categoryRepository, categoryViewMapper)
    }

    @Provides
    fun provideMainActivityViewModel(): MainActivityViewModel {
        return MainActivityViewModel()
    }

    @Provides
    fun provideStartNavViewModel(
        categoryRepository: CategoryRepository,
        categoryWithSubcategoriesViewMapper: CategoryWithSubcategoriesViewMapper
    ): StartNavViewModel {
        return StartNavViewModel(categoryRepository, categoryWithSubcategoriesViewMapper)
    }
}
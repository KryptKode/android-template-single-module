package com.kryptkode.template.app.di.screen.viewmodel

import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.categories.CategoriesViewModel
import com.kryptkode.template.categories.mapper.CategoriesViewMapper
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
        categoriesViewMapper: CategoriesViewMapper
    ): CategoriesViewModel {
        return CategoriesViewModel(categoryRepository, categoriesViewMapper)
    }
}
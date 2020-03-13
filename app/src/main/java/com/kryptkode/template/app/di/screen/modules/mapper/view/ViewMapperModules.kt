package com.kryptkode.template.app.di.screen.modules.mapper.view

import com.kryptkode.template.app.di.screen.ScreenScope
import com.kryptkode.template.cardlist.mapper.CardViewMapper
import com.kryptkode.template.categories.mapper.CategoryViewMapper
import com.kryptkode.template.startnav.mapper.CategoryWithSubcategoriesViewMapper
import com.kryptkode.template.subcategories.mapper.SubcategoryViewMapper
import dagger.Module
import dagger.Provides

/**
 * Created by kryptkode on 3/3/2020.
 */

@Module
class ViewMapperModules {
    @Provides
    @ScreenScope
    fun provideCategoryViewMapper(): CategoryViewMapper {
        return CategoryViewMapper()
    }

    @Provides
    @ScreenScope
    fun provideSubcategoryViewMapper(): SubcategoryViewMapper {
        return SubcategoryViewMapper()
    }

    @Provides
    @ScreenScope
    fun provideCategoryWithSubcategoriesViewMapper(
        categoryViewMapper: CategoryViewMapper,
        subcategoryViewMapper: SubcategoryViewMapper
    ): CategoryWithSubcategoriesViewMapper {
        return CategoryWithSubcategoriesViewMapper(categoryViewMapper, subcategoryViewMapper)
    }

    @Provides
    @ScreenScope
    fun provideCardViewMapper(): CardViewMapper {
        return CardViewMapper()
    }
}
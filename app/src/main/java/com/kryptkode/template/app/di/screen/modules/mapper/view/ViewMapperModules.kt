package com.kryptkode.template.app.di.screen.modules.mapper.view

import com.kryptkode.template.app.di.screen.ScreenScope
import com.kryptkode.template.categories.mapper.CategoriesViewMapper
import dagger.Module
import dagger.Provides

/**
 * Created by kryptkode on 3/3/2020.
 */

@Module
class ViewMapperModules {
    @Provides
    @ScreenScope
    fun provideCategoriesViewMapper(): CategoriesViewMapper {
        return CategoriesViewMapper()
    }
}
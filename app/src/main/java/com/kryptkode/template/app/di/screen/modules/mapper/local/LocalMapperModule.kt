package com.kryptkode.template.app.di.screen.modules.mapper.local

import com.kryptkode.template.app.data.local.mapper.*
import com.kryptkode.template.app.di.screen.ScreenScope
import dagger.Module
import dagger.Provides

/**
 * Created by kryptkode on 3/2/2020.
 */

@Module
class LocalMapperModule {

    @Provides
    @ScreenScope
    fun provideLocalCardMapper(): CardLocalDomainMapper {
        return CardLocalDomainMapper()
    }

    @Provides
    @ScreenScope
    fun provideLocalCategoryMapper(): CategoryLocalDomainMapper {
        return CategoryLocalDomainMapper()
    }

    @Provides
    @ScreenScope
    fun provideLocalSubcategoryMapper(): SubcategoryLocalDomainMapper {
        return SubcategoryLocalDomainMapper()
    }

    @Provides
    @ScreenScope
    fun provideLocalLinkMapper(): LinkLocalDomainMapper {
        return LinkLocalDomainMapper()
    }

    @Provides
    @ScreenScope
    fun provideLocalMappers(
        card: CardLocalDomainMapper,
        category: CategoryLocalDomainMapper,
        subcategory: SubcategoryLocalDomainMapper,
        link: LinkLocalDomainMapper
    ): LocalMappers {
        return LocalMappers(card, category, subcategory, link)
    }


}
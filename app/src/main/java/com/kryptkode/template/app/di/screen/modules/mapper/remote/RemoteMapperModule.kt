package com.kryptkode.template.app.di.screen.modules.mapper.remote

import com.kryptkode.template.app.data.remote.mapper.*
import com.kryptkode.template.app.di.screen.ScreenScope
import dagger.Module
import dagger.Provides

/**
 * Created by kryptkode on 3/3/2020.
 */
@Module
class RemoteMapperModule {
    @Provides
    @ScreenScope
    fun provideRemoteCardMapper(): CardRemoteDomainMapper {
        return CardRemoteDomainMapper()
    }

    @Provides
    @ScreenScope
    fun provideRemoteCategoryMapper(): CategoryRemoteDomainMapper {
        return CategoryRemoteDomainMapper()
    }

    @Provides
    @ScreenScope
    fun provideRemoteSubcategoryMapper(): SubcategoryRemoteDomainMapper {
        return SubcategoryRemoteDomainMapper()
    }

    @Provides
    @ScreenScope
    fun provideRemoteLinkMapper(): LinkRemoteDomainMapper {
        return LinkRemoteDomainMapper()
    }

    @Provides
    @ScreenScope
    fun provideRemoteMappers(
        card: CardRemoteDomainMapper,
        category: CategoryRemoteDomainMapper,
        subcategory: SubcategoryRemoteDomainMapper,
        link: LinkRemoteDomainMapper
    ): RemoteMappers {
        return RemoteMappers(card, category, subcategory, link)
    }
}
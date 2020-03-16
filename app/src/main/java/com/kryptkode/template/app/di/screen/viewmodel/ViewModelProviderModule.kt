package com.kryptkode.template.app.di.screen.viewmodel

import com.kryptkode.template.MainActivityViewModel
import com.kryptkode.template.app.data.domain.repository.CardRepository
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.app.data.domain.repository.SubCategoryRepository
import com.kryptkode.template.carddetails.CardDetailViewModel
import com.kryptkode.template.cardlist.CardListViewModel
import com.kryptkode.template.cardlist.mapper.CardViewMapper
import com.kryptkode.template.categories.CategoriesViewModel
import com.kryptkode.template.categories.mapper.CategoryViewMapper
import com.kryptkode.template.favoritecards.FavoriteCardsViewModel
import com.kryptkode.template.favoritecategories.FavoriteCategoriesViewModel
import com.kryptkode.template.favorites.FavoritesViewModel
import com.kryptkode.template.favoritesubcategories.FavoriteSubcategoriesViewModel
import com.kryptkode.template.startnav.StartNavViewModel
import com.kryptkode.template.startnav.mapper.CategoryWithSubcategoriesViewMapper
import com.kryptkode.template.subcategories.SubcategoriesViewModel
import com.kryptkode.template.subcategories.mapper.SubcategoryViewMapper
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

    @Provides
    fun provideSubcategoriesViewModel(
        subCategoryRepository: SubCategoryRepository,
        subcategoryViewMapper: SubcategoryViewMapper
    ): SubcategoriesViewModel {
        return SubcategoriesViewModel(subCategoryRepository, subcategoryViewMapper)
    }

    @Provides
    fun provideCardListViewModel(
        cardRepository: CardRepository,
        cardViewMapper: CardViewMapper
    ): CardListViewModel {
        return CardListViewModel(cardRepository, cardViewMapper)
    }

    @Provides
    fun provideFavoriteCardListViewModel(
        cardRepository: CardRepository,
        cardViewMapper: CardViewMapper
    ): FavoriteCardsViewModel {
        return FavoriteCardsViewModel(cardRepository, cardViewMapper)
    }

    @Provides
    fun provideFavoriteCategoriesViewModel(
        categoryRepository: CategoryRepository,
        categoryViewMapper: CategoryViewMapper
    ): FavoriteCategoriesViewModel {
        return FavoriteCategoriesViewModel(categoryRepository, categoryViewMapper)
    }

    @Provides
    fun provideFavoriteSubcategoriesViewModel(
        categoryRepository: CategoryRepository,
        categoryViewMapper: CategoryViewMapper,
        subCategoryRepository: SubCategoryRepository,
        subcategoryViewMapper: SubcategoryViewMapper
    ): FavoriteSubcategoriesViewModel {
        return FavoriteSubcategoriesViewModel(
            categoryRepository,
            subCategoryRepository,
            categoryViewMapper,
            subcategoryViewMapper
        )
    }

    @Provides
    fun provideFavoriteViewModel(): FavoritesViewModel {
        return FavoritesViewModel()
    }

    @Provides
    fun provideCardDetailViewModel(
        cardRepository: CardRepository,
        cardViewMapper: CardViewMapper
    ): CardDetailViewModel {
        return CardDetailViewModel(
            cardRepository, cardViewMapper
        )
    }
}
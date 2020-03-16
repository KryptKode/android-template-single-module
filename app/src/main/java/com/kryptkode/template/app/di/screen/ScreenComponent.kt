package com.kryptkode.template.app.di.screen

import com.kryptkode.template.MainActivity
import com.kryptkode.template.app.di.screen.modules.ScreenModule
import com.kryptkode.template.app.di.screen.viewmodel.ViewModelFactoryModule
import com.kryptkode.template.app.di.screen.viewmodel.ViewModelModule
import com.kryptkode.template.app.di.screen.viewmodel.ViewModelProviderModule
import com.kryptkode.template.carddetails.CardDetailFragment
import com.kryptkode.template.cardlist.CardListFragment
import com.kryptkode.template.categories.CategoriesFragment
import com.kryptkode.template.favoritecards.FavoriteCardsFragment
import com.kryptkode.template.favoritecategories.FavoriteCategoriesFragment
import com.kryptkode.template.favorites.FavoritesFragment
import com.kryptkode.template.favoritesubcategories.FavoriteSubcategoriesFragment
import com.kryptkode.template.startnav.StartNavFragment
import com.kryptkode.template.subcategories.SubcategoriesFragment
import dagger.Subcomponent

/**
 * Created by kryptkode on 2/19/2020.
 */

@Subcomponent(
    modules = [
        ScreenModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class,
        ViewModelProviderModule::class
    ]
)
@ScreenScope
interface ScreenComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(fragment: CategoriesFragment)
    fun inject(startNavFragment: StartNavFragment)
    fun inject(subcategoriesFragment: SubcategoriesFragment)
    fun inject(cardListFragment: CardListFragment)
    fun inject(favoritesFragment: FavoritesFragment)
    fun inject(favoriteCardsFragment: FavoriteCardsFragment)
    fun inject(favoriteCategoriesFragment: FavoriteCategoriesFragment)
    fun inject(favoriteSubcategoriesFragment: FavoriteSubcategoriesFragment)
    fun inject(cardDetailFragment: CardDetailFragment)
}
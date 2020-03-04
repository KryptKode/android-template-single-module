package com.kryptkode.template.categories.adapter

import com.kryptkode.template.categories.model.CategoryForView

/**
 * Created by kryptkode on 3/2/2020.
 */
interface CategoriesListener {
    fun onItemClick(item: CategoryForView?)
    fun onItemFavoriteClick(isFavorite: Boolean, item: CategoryForView?)
}
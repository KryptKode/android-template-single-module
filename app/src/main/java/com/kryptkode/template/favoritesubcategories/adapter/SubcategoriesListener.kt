package com.kryptkode.template.favoritesubcategories.adapter

import com.kryptkode.template.subcategories.model.SubCategoryForView

/**
 * Created by kryptkode on 3/2/2020.
 */
interface SubcategoriesListener {
    fun onItemClick(item: SubCategoryForView?)
    fun onItemFavoriteClick(isFavorite: Boolean, item: SubCategoryForView?)
}
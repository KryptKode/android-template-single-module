package com.kryptkode.template.cardlist.adapter

import com.kryptkode.template.cardlist.model.CardForView

/**
 * Created by kryptkode on 3/2/2020.
 */
interface CardListener {
    fun onItemClick(item: CardForView?)
    fun onItemFavoriteClick(isFavorite: Boolean, item: CardForView?)
}
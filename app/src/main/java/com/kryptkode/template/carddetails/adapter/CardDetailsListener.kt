package com.kryptkode.template.carddetails.adapter

import com.kryptkode.template.cardlist.model.CardForView

/**
 * Created by kryptkode on 3/2/2020.
 */
interface CardDetailsListener {
    fun onShareWhatsAppClick(cardForView: CardForView?)
    fun onShareTwitterClick(cardForView: CardForView?)
    fun onShareFacebookClick(cardForView: CardForView?)
    fun onShareOtherAppClick(cardForView: CardForView?)
    fun onItemFavoriteClick(isFavorite: Boolean, item: CardForView?)
}
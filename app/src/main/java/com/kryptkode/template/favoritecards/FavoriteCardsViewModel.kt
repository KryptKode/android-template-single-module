package com.kryptkode.template.favoritecards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CardRepository
import com.kryptkode.template.app.data.model.Event
import com.kryptkode.template.cardlist.mapper.CardViewMapper
import com.kryptkode.template.cardlist.model.CardForView

/**
 * Created by kryptkode on 3/13/2020.
 */
class FavoriteCardsViewModel (
    private val repository: CardRepository,
    private val cardViewMapper: CardViewMapper
): BaseViewModel() {

    private val goToCardDetails = MutableLiveData<Event<CardForView>>()
    fun getGoToCardDetailsEvent(): LiveData<Event<CardForView>> = goToCardDetails

    val favoriteCardList = repository.getFavoriteCards().map {
        it.map {
            cardViewMapper.mapTo(it)
        }
    }

    fun handleCardFavoriteClick(item: CardForView?, favorite: Boolean) {
        launchDataLoad {
            item?.let {
                if (favorite) {
                    repository.markCardAsFavorite(cardViewMapper.mapFrom(item))
                } else {
                    repository.unMarkCardAsFavorite(cardViewMapper.mapFrom(item))
                }
            }
        }
    }

    fun handleCardItemClick(item: CardForView?) {
        goToCardDetails.postValue(Event(item!!))
    }

    fun refresh() {
        launchDataLoad {

        }
    }
}
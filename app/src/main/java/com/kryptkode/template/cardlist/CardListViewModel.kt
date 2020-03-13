package com.kryptkode.template.cardlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CardRepository
import com.kryptkode.template.cardlist.mapper.CardViewMapper
import com.kryptkode.template.cardlist.model.CardForView

/**
 * Created by kryptkode on 3/10/2020.
 */
class CardListViewModel(
    private val repository: CardRepository,
    private val cardViewMapper: CardViewMapper
) : BaseViewModel() {

    private val subcategoryId = MutableLiveData<String>()


    val cardList = subcategoryId.switchMap {
        repository.getCardsForSubcategory(it).map {
            it.map {
                cardViewMapper.mapTo(it)
            }
        }
    }

    fun loadCards(subcategoryId: String) {
        this.subcategoryId.postValue(subcategoryId)
    }

    fun handleCardItemClick(item: CardForView?) {

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

    fun refresh(subcategoryId: String) {
        launchDataLoad {
            repository.refreshCards(subcategoryId)
        }
    }

}
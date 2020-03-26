package com.kryptkode.template.cardlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CardRepository
import com.kryptkode.template.app.data.domain.state.successOr
import com.kryptkode.template.app.data.model.Event
import com.kryptkode.template.app.utils.extensions.isNotNullOrEmpty
import com.kryptkode.template.cardlist.mapper.CardViewMapper
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.subcategories.model.SubCategoryForView

/**
 * Created by kryptkode on 3/10/2020.
 */
class CardListViewModel(
    private val repository: CardRepository,
    private val cardViewMapper: CardViewMapper
) : BaseViewModel() {

    private val goToCardDetails = MutableLiveData<Event<CardForView>>()
    fun getGoToCardDetailsEvent(): LiveData<Event<CardForView>> = goToCardDetails

    private val subcategoryId = MutableLiveData<SubCategoryForView>()
    val cardList = subcategoryId.switchMap {
        val result = repository.getCardsForSubcategory(it.id)
        addErrorAndLoadingSource (result)
        result.map {
            it.successOr(listOf()).map {
                cardViewMapper.mapTo(it)
            }
        }
    }


    fun loadCards(subcategory:SubCategoryForView) {
        if(cardList.value.isNotNullOrEmpty()){
            this.subcategoryId.postValue(subcategory)
        }
    }

    fun handleCardItemClick(item: CardForView?) {
        goToCardDetails.postValue(Event(item!!))
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
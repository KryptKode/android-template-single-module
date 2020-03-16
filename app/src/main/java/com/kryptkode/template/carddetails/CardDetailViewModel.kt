package com.kryptkode.template.carddetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CardRepository
import com.kryptkode.template.cardlist.mapper.CardViewMapper
import com.kryptkode.template.cardlist.model.CardForView

/**
 * Created by kryptkode on 3/14/2020.
 */
class CardDetailViewModel (
    private val cardRepository: CardRepository,
    private val cardViewMapper: CardViewMapper
) : BaseViewModel() {

    private val subcategoryId = MutableLiveData<String>()

    val cardList = subcategoryId.switchMap {
        cardRepository.getCardsForSubcategory(it).map {
            it.map {
                cardViewMapper.mapTo(it)
            }
        }
    }

    fun onShareWhatsApp(cardForView: CardForView?) {

    }

    fun onShareTwitter(cardForView: CardForView?) {

    }

    fun onShareFacebook(cardForView: CardForView?) {

    }

    fun onShareOtherApps(cardForView: CardForView?) {

    }

    fun toggleFavorite(item: CardForView?, favorite: Boolean) {
        launchDataLoad {
            item?.let {
                if (favorite) {
                    cardRepository.markCardAsFavorite(cardViewMapper.mapFrom(item))
                } else {
                    cardRepository.unMarkCardAsFavorite(cardViewMapper.mapFrom(item))
                }
            }
        }
    }

    fun loadData(subcategory: String) {
        this.subcategoryId.postValue(subcategory)
    }

}
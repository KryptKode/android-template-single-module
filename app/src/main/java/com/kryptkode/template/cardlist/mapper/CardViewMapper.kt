package com.kryptkode.template.cardlist.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.Card
import com.kryptkode.template.cardlist.model.CardForView

/**
 * Created by kryptkode on 3/8/2020.
 */
class CardViewMapper : Mapper<CardForView, Card> {
    override fun mapFrom(model: CardForView): Card {
        return Card(
            model.id,
            model.name,
            model.categoryId,
            model.subcategoryId,
            model.imageUrl,
            model.status,
            model.favorite
        )
    }

    override fun mapTo(model: Card): CardForView {
        return CardForView(
            model.id,
            model.name,
            model.categoryId,
            model.subcategoryId,
            model.imageUrl,
            model.status,
            model.favorite
        )
    }
}
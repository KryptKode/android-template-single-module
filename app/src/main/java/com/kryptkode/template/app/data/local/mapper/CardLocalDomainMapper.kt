package com.kryptkode.template.app.data.local.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.Card
import com.kryptkode.template.app.data.local.room.model.CardEntity

/**
 * Created by kryptkode on 3/2/2020.
 */
class CardLocalDomainMapper : Mapper<CardEntity, Card> {
    override fun mapFrom(model: CardEntity): Card {
        return Card(model.id, model.name, model.categoryId, model.subcategoryId, model.imgUrl, model.status, model.favorite)
    }

    override fun mapTo(model: Card): CardEntity {
        return CardEntity(model.id, model.name, model.categoryId, model.subcategoryId, model.imgUrl, model.status, model.favorite)
    }
}
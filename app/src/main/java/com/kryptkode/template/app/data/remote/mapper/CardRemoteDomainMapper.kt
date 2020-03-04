package com.kryptkode.template.app.data.remote.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.Card
import com.kryptkode.template.app.data.remote.response.CardRemote

/**
 * Created by kryptkode on 3/2/2020.
 */
class CardRemoteDomainMapper : Mapper<CardRemote, Card> {
    override fun mapFrom(model: CardRemote): Card {
        return Card(
            model.id,
            model.name,
            model.categoryId,
            model.subcategoryId,
            model.imgUrl,
            model.status,
            false
        )
    }

    override fun mapTo(model: Card): CardRemote {
        return CardRemote(
            model.id,
            model.name,
            model.categoryId,
            model.subcategoryId,
            model.imgUrl,
            model.status
        )
    }
}
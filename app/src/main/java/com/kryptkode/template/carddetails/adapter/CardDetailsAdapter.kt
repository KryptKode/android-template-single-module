package com.kryptkode.template.carddetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kryptkode.template.app.base.recycler.BaseRecyclerAdapter
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.databinding.ItemCardDetailBinding

/**
 * Created by kryptkode on 3/2/2020.
 */
class CardDetailsAdapter (private val cardDetailsListener: CardDetailsListener) : BaseRecyclerAdapter<CardForView, CardDetailsViewHolder>(CardForView.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDetailsViewHolder {
        return CardDetailsViewHolder(cardDetailsListener, ItemCardDetailBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }
}
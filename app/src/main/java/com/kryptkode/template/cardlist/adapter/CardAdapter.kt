package com.kryptkode.template.cardlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kryptkode.template.app.base.recycler.BaseRecyclerAdapter
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.databinding.ItemCategoryGridBinding

/**
 * Created by kryptkode on 3/2/2020.
 */
class CardAdapter (private val cardListener: CardListener) : BaseRecyclerAdapter<CardForView, CardViewHolder>(CardForView.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(cardListener, ItemCategoryGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }
}
package com.kryptkode.template.cardlist.adapter

import coil.api.load
import com.kryptkode.template.R
import com.kryptkode.template.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.template.app.customviews.PlaceHolderDrawable
import com.kryptkode.template.app.utils.ImageUrl.getImageUrl
import com.kryptkode.template.app.utils.extensions.beInvisible
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.databinding.ItemCategoryGridBinding

/**
 * Created by kryptkode on 3/2/2020.
 */
class CardViewHolder(
    private val cardListener: CardListener,
    binding: ItemCategoryGridBinding) :
    BaseRecyclerViewHolder<CardForView, ItemCategoryGridBinding>(binding) {
    private val placeholderDrawable = PlaceHolderDrawable(binding.root.context)
    override fun performBind(item: CardForView?) {

        binding.tvName.beInvisible()
        binding.imgLock.beInvisible()
        binding.imgBtnFavourite.isChecked = item?.favorite ?: false
        binding.imgThumbnail.load(getImageUrl(item?.imageUrl)){
            placeholder(placeholderDrawable)
            error(R.mipmap.ic_launcher)
        }

        binding.imgBtnFavourite.setOnClickListener {
            cardListener.onItemFavoriteClick(binding.imgBtnFavourite.isChecked, item)
        }

        binding.rootCardView.setOnClickListener {
            cardListener.onItemClick(item)
        }
    }
}
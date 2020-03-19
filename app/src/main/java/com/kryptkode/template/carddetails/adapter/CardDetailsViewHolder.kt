package com.kryptkode.template.carddetails.adapter

import coil.api.load
import coil.request.CachePolicy
import com.kryptkode.template.R
import com.kryptkode.template.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.template.app.customviews.PlaceHolderDrawable
import com.kryptkode.template.app.utils.ImageUrl.getImageUrl
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.databinding.ItemCardDetailBinding

/**
 * Created by kryptkode on 3/2/2020.
 */
class CardDetailsViewHolder(
    private val cardDetailsListener: CardDetailsListener,
    binding: ItemCardDetailBinding
) :
    BaseRecyclerViewHolder<CardForView, ItemCardDetailBinding>(binding) {
    private val placeholderDrawable = PlaceHolderDrawable(binding.root.context)
    override fun performBind(item: CardForView?) {

        binding.imgBtnFavourite.isChecked = item?.favorite ?: false
        binding.image.load(getImageUrl(item?.imageUrl)) {
            diskCachePolicy(CachePolicy.ENABLED)
            placeholder(placeholderDrawable)
            error(R.mipmap.ic_launcher)
        }

        binding.imgBtnFavourite.setOnClickListener {
            cardDetailsListener.onItemFavoriteClick(binding.imgBtnFavourite.isChecked, item)
        }

        binding.btnShareFaceBook.setOnClickListener {
            cardDetailsListener.onShareFacebookClick(item)
        }

        binding.btnShareTwitter.setOnClickListener {
            cardDetailsListener.onShareTwitterClick(item)
        }

        binding.btnShareWhatsApp.setOnClickListener {
            cardDetailsListener.onShareWhatsAppClick(item)
        }

        binding.btnShareOthers.setOnClickListener {
            cardDetailsListener.onShareOtherAppClick(item)
        }
    }
}
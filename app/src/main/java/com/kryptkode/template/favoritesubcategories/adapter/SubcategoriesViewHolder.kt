package com.kryptkode.template.favoritesubcategories.adapter

import coil.api.load
import com.kryptkode.template.R
import com.kryptkode.template.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.template.app.customviews.PlaceHolderDrawable
import com.kryptkode.template.app.utils.ImageUrl.getImageUrl
import com.kryptkode.template.databinding.ItemCategoryGridBinding
import com.kryptkode.template.subcategories.model.SubCategoryForView

/**
 * Created by kryptkode on 3/2/2020.
 */
class SubcategoriesViewHolder(
    private val subcategoriesListener: SubcategoriesListener,
    binding: ItemCategoryGridBinding) :
    BaseRecyclerViewHolder<SubCategoryForView, ItemCategoryGridBinding>(binding) {
    private val placeholderDrawable = PlaceHolderDrawable(binding.root.context)
    override fun performBind(item: SubCategoryForView?) {

        binding.tvName.text = item?.name?.capitalize()
        binding.imgBtnFavourite.isChecked = item?.favorite ?: false
        binding.imgThumbnail.load(getImageUrl(item?.imageUrl)){
            placeholder(placeholderDrawable)
            error(R.mipmap.ic_launcher)
        }

        binding.imgBtnFavourite.setOnClickListener {
            subcategoriesListener.onItemFavoriteClick(binding.imgBtnFavourite.isChecked, item)
        }

        binding.rootCardView.setOnClickListener {
            subcategoriesListener.onItemClick(item)
        }
    }
}
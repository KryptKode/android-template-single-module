package com.kryptkode.template.categories.adapter

import coil.api.load
import com.kryptkode.template.R
import com.kryptkode.template.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.template.app.customviews.PlaceHolderDrawable
import com.kryptkode.template.app.utils.ImageUrl.getImageUrl
import com.kryptkode.template.app.utils.extensions.beVisibleIf
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.databinding.ItemCategoryGridBinding

/**
 * Created by kryptkode on 3/2/2020.
 */
class CategoriesViewHolder(
    private val categoriesListener: CategoriesListener,
    binding: ItemCategoryGridBinding) :
    BaseRecyclerViewHolder<CategoryForView, ItemCategoryGridBinding>(binding) {
    private val placeholderDrawable = PlaceHolderDrawable(binding.root.context)
    override fun performBind(item: CategoryForView?) {

        binding.tvName.text = item?.name?.capitalize()
        binding.imgLock.beVisibleIf(item?.locked ?: false)
        binding.imgBtnFavourite.isChecked = item?.favorite ?: false
        binding.imgThumbnail.load(getImageUrl(item?.imageUrl)){
            placeholder(placeholderDrawable)
            error(R.mipmap.ic_launcher)
        }

        binding.imgBtnFavourite.setOnClickListener {
            categoriesListener.onItemFavoriteClick(binding.imgBtnFavourite.isChecked, item)
        }

        binding.rootCardView.setOnClickListener {
            categoriesListener.onItemClick(item)
        }
    }
}
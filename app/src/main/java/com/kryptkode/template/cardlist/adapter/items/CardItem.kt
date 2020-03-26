package com.kryptkode.template.cardlist.adapter.items

import coil.api.load
import com.kryptkode.template.R
import com.kryptkode.template.app.customviews.PlaceHolderDrawable
import com.kryptkode.template.app.utils.ImageUrl
import com.kryptkode.template.app.utils.extensions.beInvisible
import com.kryptkode.template.cardlist.adapter.CardListener
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.databinding.ItemCategoryGridBinding
import com.xwray.groupie.databinding.BindableItem

/**
 * Created by kryptkode on 3/23/2020.
 */
class CardItem  (val item: CardForView, private val cardListener: CardListener): BindableItem<ItemCategoryGridBinding>() {

    override fun bind(viewBinding: ItemCategoryGridBinding, position: Int) {
        val placeholderDrawable = PlaceHolderDrawable(viewBinding.root.context)
        viewBinding.tvName.beInvisible()
        viewBinding.imgLock.beInvisible()
        viewBinding.imgBtnFavourite.isChecked = item.favorite
        viewBinding.imgThumbnail.load(ImageUrl.getImageUrl(item.imageUrl)){
            placeholder(placeholderDrawable)
            error(R.mipmap.ic_launcher)
        }

        viewBinding.imgBtnFavourite.setOnClickListener {
            cardListener.onItemFavoriteClick(viewBinding.imgBtnFavourite.isChecked, item)
        }

        viewBinding.rootCardView.setOnClickListener {
            cardListener.onItemClick(item)
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_category_grid
    }

    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return 1
    }
}
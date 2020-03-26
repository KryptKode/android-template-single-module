package com.kryptkode.template.cardlist.adapter.items

import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.kryptkode.template.R
import com.kryptkode.template.databinding.ItemNativeAdBinding
import com.xwray.groupie.databinding.BindableItem

/**
 * Created by kryptkode on 3/23/2020.
 */
class NativeAdItem(val unifiedNativeAd: UnifiedNativeAd) : BindableItem<ItemNativeAdBinding>() {

    override fun bind(viewBinding: ItemNativeAdBinding, position: Int) {
        viewBinding.nativeAd.setNativeAd(unifiedNativeAd)
    }

    override fun getLayout(): Int {
        return R.layout.item_native_ad
    }

    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return when (getItem(position)) {
            is NativeAdItem -> 2
            else -> 1
        }
    }
}
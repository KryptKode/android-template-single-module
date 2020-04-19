package com.kryptkode.template.cardlist.adapter.items

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.kryptkode.template.R
import com.kryptkode.template.app.utils.extensions.beGone
import com.kryptkode.template.app.utils.extensions.beVisible
import com.kryptkode.template.databinding.ItemNativeAdBinding
import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem

/**
 * Created by kryptkode on 3/23/2020.
 */
class NativeAdItem(val unifiedNativeAd: UnifiedNativeAd?) : BindableItem<ItemNativeAdBinding>() {

    override fun bind(viewBinding: ItemNativeAdBinding, position: Int) {
        if (unifiedNativeAd == null) {
            viewBinding.itemRoot.beGone()
        } else {
            viewBinding.itemRoot.beVisible()
            viewBinding.itemRoot.layoutParams.apply {
                height = WRAP_CONTENT
                width = MATCH_PARENT
            }
            viewBinding.nativeAd.setNativeAd(unifiedNativeAd)
        }
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NativeAdItem) return false

        if (unifiedNativeAd != other.unifiedNativeAd) return false

        return true
    }

    override fun hashCode(): Int {
        return unifiedNativeAd?.hashCode() ?: 0
    }

    override fun isSameAs(other: Item<*>?): Boolean {
        return if (other is NativeAdItem) {
            unifiedNativeAd.hashCode() == other.unifiedNativeAd.hashCode()
        } else {
            super.isSameAs(other)
        }
    }

    override fun hasSameContentAs(other: Item<*>?): Boolean {
        return if (other is NativeAdItem) {
            unifiedNativeAd == other.unifiedNativeAd
        } else {
            super.hasSameContentAs(other)
        }
    }

    override fun getId(): Long {
        return unifiedNativeAd?.hashCode()?.toLong() ?: super.getId()
    }
}
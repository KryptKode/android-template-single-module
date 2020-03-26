package com.kryptkode.template.cardlist.adapter

import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.kryptkode.template.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.template.databinding.ItemNativeAdBinding


class NativeAdAdvancedViewHolder(binding: ItemNativeAdBinding) :
    BaseRecyclerViewHolder<UnifiedNativeAd, ItemNativeAdBinding>(binding) {
    override fun performBind(item: UnifiedNativeAd?) {
        binding.nativeAd.setNativeAd(item)
    }
}
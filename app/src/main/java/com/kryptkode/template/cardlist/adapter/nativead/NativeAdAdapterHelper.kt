package com.kryptkode.template.cardlist.adapter.nativead

import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.kryptkode.template.ads.NativeAdRowHelper
import com.kryptkode.template.cardlist.adapter.items.NativeAdItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import timber.log.Timber

/**
 * Created by kryptkode on 3/23/2020.
 */
class NativeAdAdapterHelper(
    val adapter: GroupAdapter<GroupieViewHolder>,
    private val rowHelper: NativeAdRowHelper,
    private val name: String
) {
    private val nativeAds = mutableListOf<UnifiedNativeAd>()
    private val nativeAdRows = rowHelper.getNativeAdRows()

    fun addNativeAd(unifiedNativeAd: UnifiedNativeAd) {
        nativeAds.add(unifiedNativeAd)
    }

    fun updateAdapterWithNativeAds(startPosition: Int, itemCount: Int) {
        rowHelper.populateNativeAdRows(startPosition, itemCount)
        showNativeAdIfLoaded()
    }

    private fun hasNativeAds(): Boolean {
        return nativeAds.isNotEmpty()
    }

    private fun isNativeAdPosition(position: Int): Boolean {
        return hasNativeAds() && nativeAds.size > position
    }

    private fun noNativeAdAtPosition(position: Int): Boolean {
        val item = adapter.getItem(position)
        return if (item is NativeAdItem) {
            item.unifiedNativeAd == null
        } else {
            true
        }
    }

    private fun canPlaceNativeAdAt(position: Int): Boolean {
        return isNativeAdPosition(position) && noNativeAdAtPosition(position)
    }

    fun removeNativeAdsIfLoaded() {
        if (hasNativeAds()) {
            Timber.e("Removing native ad")
            nativeAdRows.forEach {
                Timber.e("Removing native ad from position $it")
                adapter.notifyItemRemoved(it)
            }
        }
    }

    private fun showNativeAdIfLoaded() {
        if (hasNativeAds()) {
            nativeAdRows.forEach { position ->
                val adPosition = position % nativeAds.size
                if (canPlaceNativeAdAt(adPosition)) {
                    Timber.d("Getting native ad row [$name]... $adPosition")
                    val nativeAd = nativeAds[adPosition]
                    val nativeAdItem = NativeAdItem(nativeAd)
                    adapter.add(position, nativeAdItem)
                } else {
                    Timber.d("$adPosition is not a valid ad position  [$name]")
                }
            }
        }
    }
}
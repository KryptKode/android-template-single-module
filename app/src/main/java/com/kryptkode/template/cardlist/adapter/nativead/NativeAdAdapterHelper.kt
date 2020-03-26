package com.kryptkode.template.cardlist.adapter.nativead

import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.kryptkode.template.ads.AdConfig
import com.kryptkode.template.cardlist.adapter.items.NativeAdItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import timber.log.Timber

/**
 * Created by kryptkode on 3/23/2020.
 */
class NativeAdAdapterHelper (val adapter: GroupAdapter<GroupieViewHolder>){
    private val nativeAds = mutableListOf<UnifiedNativeAd>()
    private val nativeAdRows = mutableListOf<Int>()

    fun addNativeAd(unifiedNativeAd: UnifiedNativeAd){
        nativeAds.add(unifiedNativeAd)
    }

    fun updateAdapterWithNativeAds() {
        populateNativeAdRows()
        showNativeAdIfLoaded()
    }

    private fun hasNativeAds(): Boolean {
        return nativeAds.isNotEmpty()
    }

    private fun isNativeAdPosition(position: Int): Boolean {
        return hasNativeAds() && nativeAds.size > position
    }

    fun removeNativeAdsIfLoaded() {
        if (hasNativeAds()) {
            Timber.e( "Removing native ad")
            nativeAdRows.forEach {
                Timber.e("Removing native ad from position $it")
                adapter.notifyItemRemoved(it)
            }
            nativeAdRows.clear()
        }
    }

    private fun showNativeAdIfLoaded() {
        if (hasNativeAds()) {
            nativeAdRows.forEach {position->
                val adPosition = position % nativeAds.size
                if(isNativeAdPosition(adPosition)){
                    Timber.d("Getting native ad row... $adPosition")
                    val nativeAd = nativeAds[adPosition]
                    val nativeAdItem = NativeAdItem(nativeAd)
                    adapter.add(position, nativeAdItem)
                }else{
                    Timber.d("$adPosition is not a valid ad position")
                }
            }
        }
    }


    private fun getTotalItems():Int{
        return adapter.groupCount
    }

    private fun populateNativeAdRows() {
        nativeAdRows.clear()
        val totalItems = getTotalItems()
        for (i in 0..totalItems) {
            Timber.d("Native ad to be added in row: $i total--> $totalItems")
            if (i != 0 && i % AdConfig.NATIVE_AD_AFTER_POSTS == 0) {
                nativeAdRows.add(i)
            }
        }

        if (nativeAdRows.isEmpty()) {
            //add to the end
            nativeAdRows.add(totalItems)
        }
        Timber.d("Native ad rows: $nativeAdRows")
    }


}
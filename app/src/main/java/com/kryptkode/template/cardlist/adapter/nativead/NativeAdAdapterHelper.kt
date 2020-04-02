package com.kryptkode.template.cardlist.adapter.nativead

import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.kryptkode.template.ads.AdConfig
import com.kryptkode.template.cardlist.adapter.items.NativeAdItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import timber.log.Timber
import kotlin.math.floor

/**
 * Created by kryptkode on 3/23/2020.
 */
class NativeAdAdapterHelper(
    val adapter: GroupAdapter<GroupieViewHolder>,
    private val name: String
) {
    private val nativeAds = mutableListOf<UnifiedNativeAd>()
    private val nativeAdRows = mutableListOf<Int>()

    fun addNativeAd(unifiedNativeAd: UnifiedNativeAd) {
        nativeAds.add(unifiedNativeAd)
    }

    fun updateAdapterWithNativeAds(startPosition: Int, itemCount: Int) {
        populateNativeAdRows(startPosition, itemCount)
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
            Timber.e("Removing native ad")
            nativeAdRows.forEach {
                Timber.e("Removing native ad from position $it")
                adapter.notifyItemRemoved(it)
            }
            nativeAdRows.clear()
        }
    }

    private fun showNativeAdIfLoaded() {
        if (hasNativeAds()) {
            nativeAdRows.forEach { position ->
                val adPosition = position % nativeAds.size
                if (isNativeAdPosition(adPosition)) {
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


    private fun populateNativeAdRows(startPosition: Int, itemCount: Int) {
        nativeAdRows.clear()
        for (i in startPosition..itemCount) {
            Timber.d("Native ad to be added in row  [$name]: $i total--> $itemCount")
            if (numberIsNativeAdPosition(i)) {
                Timber.d("Adding position [$name]: $i")
                nativeAdRows.add(i)
            }
        }

        if (nativeAdRows.isEmpty()) {
            //add to the end
            nativeAdRows.add(itemCount)
        }
        Timber.d("Native ad rows [$name]: $nativeAdRows")
    }

    private fun numberIsNativeAdPosition(number:Int):Boolean{
        val commonDifference = AdConfig.NATIVE_AD_AFTER_POSTS + 1
        val positionInSequence = ((number +  commonDifference) + 1.0) / commonDifference
        return isWholeNumber(positionInSequence)
    }

    private fun isWholeNumber(number:Double): Boolean {
        return number == floor(number) && number.isFinite()
    }


}
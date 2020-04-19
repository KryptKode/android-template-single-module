package com.kryptkode.template.ads

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.kryptkode.template.R
import timber.log.Timber

class NativeAdHelper(private val context: Context) {

    private var adLoader: AdLoader? = null
    private val adList = mutableListOf<UnifiedNativeAd>()

    val isLoaded: Boolean
        get() = adList.isNotEmpty()


    fun getNativeAd(position: Int? = null): UnifiedNativeAd? {
        return if (adList.isNotEmpty()) {
            if (position == null) {
                adList.random()
            } else {
                val size = adList.size
                adList[position % size]
            }
        } else {
            null
        }
    }

    fun loadNativeAds(listener: NativeAdListener? = null) {

        if (adList.isEmpty()) {
            initAds(context, listener)
        } else {
            for (unifiedNativeAd in adList) {
                listener?.addNativeAd(unifiedNativeAd)
            }
            listener?.onAdLoaded()
        }

    }

    private fun initAds(context: Context, listener: NativeAdListener?) {

        val videoOptions = VideoOptions.Builder()
            .setStartMuted(false)
            .build()

        val adOptions = NativeAdOptions.Builder()
            .setVideoOptions(videoOptions)
            .build()
        adLoader = AdLoader.Builder(context, context.getString(R.string.native_ad_unit_id))
            .forUnifiedNativeAd {
                adList.add(it)
                listener?.addNativeAd(it)
                Timber.d("Native ad loaded ${it.body}")
                if (adLoader?.isLoading == false) {
                    listener?.onAdLoaded()
                }
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: Int) {
                    Timber.e("Native ad failed to load $p0")
                    if (adLoader?.isLoading == false) {
                        listener?.onAdFailed()
                    }
                }
            }).withNativeAdOptions(adOptions).build()

        adLoader?.loadAds(AdRequest.Builder().build(), AdConfig.NUM_NATIVE_ADS)
    }

    companion object {
        private var INSTANCE: NativeAdHelper? = null
        private val lock = Any()

        fun getInstance(context: Context): NativeAdHelper {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = NativeAdHelper(context)
                    }
                    return INSTANCE as NativeAdHelper
                }
            }
            return INSTANCE as NativeAdHelper
        }
    }


    interface NativeAdListener {
        fun onAdLoaded()
        fun onAdFailed()
        fun addNativeAd(unifiedNativeAd: UnifiedNativeAd)
    }
}
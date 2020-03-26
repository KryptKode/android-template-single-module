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

class NativeAdHelper(private val context: Context,
                     private val listener: NativeAdListener) {

    private var adLoader: AdLoader? = null

    fun loadNativeAds(number: Int = AdConfig.NUM_NATIVE_ADS) {
        val videoOptions = VideoOptions.Builder()
                .setStartMuted(false)
                .build()

        val adOptions = NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build()


        adLoader = AdLoader.Builder(context, context.getString(R.string.native_ad_unit_id))
                .forUnifiedNativeAd {
                    listener.addNativeAd(it)
                    Timber.d( "Native ad loaded ${it.body}")
                    if (adLoader?.isLoading == false) {
                        listener.onAdLoaded()
                    }
                }.withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(p0: Int) {
                        Timber.e("Native ad failed to load $p0")
                        if (adLoader?.isLoading == false) {
                            listener.onAdFailed()
                        }
                    }


                }).withNativeAdOptions(adOptions).build()

        adLoader?.loadAds(AdRequest.Builder().build(), number)
    }


    interface NativeAdListener {
        fun onAdLoaded()
        fun onAdFailed()
        fun addNativeAd(unifiedNativeAd: UnifiedNativeAd)
    }
}
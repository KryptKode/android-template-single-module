package com.kryptkode.adbase

import android.view.ViewGroup

interface Ad {
    fun initBannerAd(bannerContainer: ViewGroup)
    fun initInterstitialAd()
    fun showInterstitialAd()
    fun showInterstitialAdImmediately()
    fun initRewardedVideoAd()
    fun showRewardedVideoAd()

}

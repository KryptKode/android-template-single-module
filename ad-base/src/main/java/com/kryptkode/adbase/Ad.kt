package com.kryptkode.adbase

import android.view.ViewGroup
import com.kryptkode.adbase.rewardedvideo.RewardedVideoListener

interface Ad {
    fun initBannerAd(bannerContainer: ViewGroup)
    fun initInterstitialAd()
    fun showInterstitialAd()
    fun showInterstitialAdImmediately()
    fun initRewardedVideoAd(listener: RewardedVideoListener?=null)
    fun showRewardedVideoAd()
    fun showRewardedVideoAdImmediately():Boolean
}

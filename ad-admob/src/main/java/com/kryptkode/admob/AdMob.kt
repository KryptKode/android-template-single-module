package com.kryptkode.admob

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.kryptkode.adbase.AdType
import com.kryptkode.adbase.base.BaseAd
import com.kryptkode.adbase.intersitial.InterstitialAdNotLoadedListener
import com.kryptkode.adbase.rewardedvideo.Reward
import com.kryptkode.adbase.rewardedvideo.RewardedVideoAdNotLoadedListener
import com.kryptkode.adbase.rewardedvideo.RewardedVideoListener
import com.kryptkode.adbase.utils.DeviceUtils
import timber.log.Timber

class AdMob(activity: Activity) : BaseAd(activity) {
    private var interstitialAd: InterstitialAd? = null
    private var interstitialAdNotLoadedListener: InterstitialAdNotLoadedListener? = null
    private var rewardedVideoAd: RewardedAd? = null
    private var rewardedVideoListener: RewardedVideoListener? = null
    private var rewardedVideoAdNotLoadedListener: RewardedVideoAdNotLoadedListener? = null
    private val interstitialUnitId = activity.getString(R.string.admob_interstitial_id)
    private val rewardedVideoUnitId = activity.getString(R.string.admob_rewarded_video_ad_unit_id)

    private val testDevices = mutableListOf(AdRequest.DEVICE_ID_EMULATOR)

    override val adType: AdType
        get() = AdType.AD_MOB

    init {
        addUserDeviceIfDebugBuild()
        initTestDevices()
    }

    private fun initTestDevices() {
        val requestConfiguration = RequestConfiguration.Builder()
            .setTestDeviceIds(testDevices)
            .build()
        MobileAds.setRequestConfiguration(requestConfiguration)
    }

    private fun addUserDeviceIfDebugBuild() {
        if(BuildConfig.DEBUG){
            testDevices.add(DeviceUtils.getDeviceID(activity))
        }
    }

    override fun initBannerAd(bannerContainer: ViewGroup) {
        val bannerId = activity.getString(R.string.admob_banner_id)
        if (bannerId.isNotEmpty()) {
            val adView = AdView(activity)
            adView.adUnitId = bannerId
            adView.adSize = AdSize.SMART_BANNER
            bannerContainer.addView(adView)
            adView.adListener = object: AdListener(){
                override fun onAdFailedToLoad(reason: Int) {
                    Timber.e("Ad failed to load with code $reason")
                }
            }
            bannerContainer.visibility = View.VISIBLE
            val adRequest = AdRequest.Builder()
            adView.loadAd(adRequest.build())
        } else {
            Timber.e("Admob ID not found: Place the string ID with name 'admob_banner_id' as a string resource ")
        }
    }

    override fun initInterstitialAd() {
        if (interstitialUnitId.isNotEmpty()) {

            interstitialAd = InterstitialAd(activity)
            interstitialAd?.adUnitId = interstitialUnitId

            val adRequest = AdRequest.Builder()

            interstitialAd?.loadAd(adRequest.build())
            interstitialAd?.adListener = object : AdListener() {
                override fun onAdClosed() {
                    interstitialAd?.loadAd(adRequest.build())
                }
            }
        } else {
            logInterstitialUnitIdNotFoundError()
        }


    }

    override fun showInterstitialAd() {
        if (interstitialUnitId.isNotEmpty()) {
            checkCounterToShowInterstitialOrIncrementCounter()
        } else {
            logInterstitialUnitIdNotFoundError()
        }
    }

    override fun showInterstitialAdImmediately() {
        showInterstitialAdIfLoaded()
    }

    private fun checkCounterToShowInterstitialOrIncrementCounter() {
        if (interstitialCounter == INTERSTITIAL_INTERVAL - 1) {
            if (showInterstitialAdIfLoaded()) {
                resetInterstitialAdCounter()
            }
        } else {
            incrementInterstitialCounter()
        }
    }

    private fun showInterstitialAdIfLoaded(): Boolean {
        if (interstitialAd?.isLoaded == true) {
            interstitialAd?.show()
            return true
        }else{
            interstitialAdNotLoadedListener?.onLoadFail()
        }
        return false
    }

    private fun resetInterstitialAdCounter() {
        interstitialCounter = 0
    }

    private fun incrementInterstitialCounter() {
        interstitialCounter++
    }

    private fun logInterstitialUnitIdNotFoundError() {
        Timber.e("AdMob ID not found: Place the string ID with name 'admob_interstitial_id' as a string resource ")
    }

    override fun initRewardedVideoAd(listener: RewardedVideoListener?) {
        if (rewardedVideoUnitId.isNotEmpty()) {
            this.rewardedVideoListener = listener
            rewardedVideoAd = RewardedAd(activity, rewardedVideoUnitId)
        } else {
            Timber.e("Admob ID not found: Place the string ID with name 'admob_rewarded_video_ad_unit_id' as a string resource ")
        }

        loadRewardedVideoAd()
    }

    private fun loadRewardedVideoAd() {
        val adRequest = AdRequest.Builder()

        val adLoadCallback = object : RewardedAdLoadCallback(){
            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                Timber.e("Rewarded video ad failed to load: $errorCode")
                rewardedVideoListener?.onRewardedVideoAdFailedToLoad(errorCode)
            }

            override fun onRewardedAdLoaded() {
                Timber.i("Rewarded video ad loaded")
                rewardedVideoListener?.onRewardedVideoAdLoaded()
            }
        }

        rewardedVideoAd?.loadAd(adRequest.build(), adLoadCallback)
    }

    override fun showRewardedVideoAd() {
        if (rewardedVideoUnitId.isNotEmpty()) {

            if (rewardedVideoCounter == REWARDED_VIDEO_INTERVAL - 1) {
                if (showVideoAdIfLoaded()) {
                    rewardedVideoCounter = 0
                }
            } else {
                rewardedVideoCounter++
            }
        } else {
            Timber.e("Admob ID not found: Place the string ID with name 'admob_rewarded_video_ad_unit_id' as a string resource ")
        }
    }

    private fun showVideoAdIfLoaded(): Boolean {
        val adCallback =  object : RewardedAdCallback(){
            override fun onUserEarnedReward(reward: com.google.android.gms.ads.rewarded.RewardItem) {
                rewardedVideoListener?.onRewarded(Reward(reward.type, reward.amount))
            }

            override fun onRewardedAdFailedToShow(errorCode: Int) {
                Timber.e("Rewarded video ad failed to show: $errorCode")
                rewardedVideoListener?.onRewardedAdFailedToShow(errorCode)
            }

            override fun onRewardedAdClosed() {
                rewardedVideoListener?.onRewardedVideoAdClosed()
            }

            override fun onRewardedAdOpened() {
                rewardedVideoListener?.onRewardedVideoAdOpened()
            }
        }
        return if (rewardedVideoAd?.isLoaded == true) {
            rewardedVideoAd?.show(activity, adCallback)
            true
        }else{
            rewardedVideoAdNotLoadedListener?.onLoadFail()
            false
        }
    }

    override fun showRewardedVideoAdImmediately(): Boolean {
        return showVideoAdIfLoaded()
    }


}

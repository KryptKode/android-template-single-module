package com.kryptkode.admob

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.*
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.kryptkode.adbase.*
import timber.log.Timber

class AdMob(activity: Activity) : BaseAd(activity) {
    private var interstitialAd: InterstitialAd? = null
    var interstitialAdNotLoadedListener: InterstitialAdNotLoadedListener? = null
    private var rewardedVideoAd: RewardedVideoAd? = null
    var rewardedVideoAdListener: RewardedVideoListener? = null
    var rewardedVideoAdNotLoadedListener: RewardedVideoAdNotLoadedListener? = null
    private val interstitialUnitId = activity.getString(R.string.admob_interstitial_id)
    private val rewardedVideoUnitId = activity.getString(R.string.admob_rewarded_video_ad_unit_id)

    override val adType: AdType
        get() = AdType.AD_MOB

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
            val adRequest = AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            //add the current device ID as test ID for debug builds
            if (BuildConfig.DEBUG) {
                adRequest.addTestDevice(DeviceUtils.getDeviceID(activity))
            }
            adView.loadAd(adRequest.build())
        } else {
            Timber.e("Admob ID not found: Place the string ID with name 'admob_banner_id' as a string resource ")
        }
    }

    override fun initInterstitialAd() {
        if (interstitialUnitId.isNotEmpty()) {

            interstitialAd = InterstitialAd(activity)
            interstitialAd?.adUnitId = interstitialUnitId

            val adRequest = AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            //add the current device ID as test ID for debug builds
            if (BuildConfig.DEBUG) {
                adRequest.addTestDevice(DeviceUtils.getDeviceID(activity))
            }
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

    override fun initRewardedVideoAd() {
        if (rewardedVideoUnitId.isNotEmpty()) {
            rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity)
            rewardedVideoAd?.rewardedVideoAdListener = object : RewardedVideoAdListener {
                override fun onRewardedVideoAdLeftApplication() {
                    rewardedVideoAdListener?.onRewardedVideoAdLeftApplication()
                }

                override fun onRewardedVideoAdLoaded() {
                    rewardedVideoAdListener?.onRewardedVideoAdLoaded()
                }

                override fun onRewardedVideoAdOpened() {
                    rewardedVideoAdListener?.onRewardedVideoAdOpened()
                }

                override fun onRewardedVideoCompleted() {
                    rewardedVideoAdListener?.onRewardedVideoCompleted()
                }

                override fun onRewarded(rewardItem: RewardItem?) {
                    val reward = Reward(rewardItem?.type, rewardItem?.amount)
                    rewardedVideoAdListener?.onRewarded(reward)
                }

                override fun onRewardedVideoStarted() {
                    rewardedVideoAdListener?.onRewardedVideoStarted()
                }

                override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
                    rewardedVideoAdListener?.onRewardedVideoAdFailedToLoad(errorCode)
                }

                override fun onRewardedVideoAdClosed() {
                    rewardedVideoAdListener?.onRewardedVideoAdClosed()
                    loadRewardedVideoAd()
                }
            }

        } else {
            Timber.e("Admob ID not found: Place the string ID with name 'admob_rewarded_video_ad_unit_id' as a string resource ")
        }

        loadRewardedVideoAd()
    }

    private fun loadRewardedVideoAd() {
        val adRequest = AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        //add the current device ID as test ID for debug builds
        if (BuildConfig.DEBUG) {
            adRequest.addTestDevice(DeviceUtils.getDeviceID(activity))
        }
        rewardedVideoAd?.loadAd(rewardedVideoUnitId, adRequest.build())
    }

    override fun showRewardedVideoAd() {
        if (rewardedVideoUnitId.isNotEmpty()) {

            if (rewardedVideoCounter == REWARDED_VIDEO_INTERVAL - 1) {
                if (rewardedVideoAd?.isLoaded == true) {
                    rewardedVideoAd?.show()
                    rewardedVideoCounter = 0
                }else{
                    rewardedVideoAdNotLoadedListener?.onLoadFail()
                }
            } else {
                rewardedVideoCounter++
            }
        } else {
            Timber.e("Admob ID not found: Place the string ID with name 'admob_rewarded_video_ad_unit_id' as a string resource ")
        }
    }


}

package com.kryptkode.adbase.rewardedvideo

open class RewardedVideoListener{
        open fun onRewardedVideoAdLoaded(){

        }

        open fun onRewardedVideoAdOpened(){

        }

        open fun onRewardedVideoAdClosed(){

        }

        open fun onRewarded(reward: Reward?){

        }


        open fun onRewardedVideoAdFailedToLoad(errorCode: Int){

        }


        open fun onRewardedAdFailedToShow(errorCode: Int) {

        }
}
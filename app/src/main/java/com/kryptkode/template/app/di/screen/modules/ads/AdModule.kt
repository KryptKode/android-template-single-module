package com.kryptkode.template.app.di.screen.modules.ads

import androidx.fragment.app.FragmentActivity
import com.kryptkode.adbase.Ad
import com.kryptkode.admob.AdMob
import com.kryptkode.template.ads.NativeAdHelper
import com.kryptkode.template.app.di.screen.ScreenScope
import dagger.Module
import dagger.Provides

/**
 * Created by kryptkode on 3/21/2020.
 */
@Module
class AdModule {

    @Provides
    @ScreenScope
    fun provideAd(fragmentActivity:FragmentActivity): Ad{
        return AdMob(fragmentActivity)
    }

    @Provides
    @ScreenScope
    fun provideNativeAdHelper(): NativeAdHelper{
        return NativeAdHelper.getInstance()
    }
}
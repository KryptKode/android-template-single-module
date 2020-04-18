package com.kryptkode.template.ads

import timber.log.Timber
import kotlin.math.floor

/**
 * Created by kryptkode on 4/18/2020.
 */
class NativeAdRowHelper {
    private val nativeAdRows = mutableListOf<Int>()

    fun getNativeAdRows(): List<Int> {
        return nativeAdRows
    }

    fun populateNativeAdRows(startPosition: Int, itemCount: Int) {
        nativeAdRows.clear()
        for (i in startPosition..itemCount) {
            Timber.d("Native ad to be added in row : $i total--> $itemCount")
            if (numberIsNativeAdPosition(i)) {
                Timber.d("Adding position: $i")
                nativeAdRows.add(i)
            }
        }

        if (nativeAdRows.isEmpty()) {
            //add to the end
            nativeAdRows.add(itemCount)
        }
        Timber.d("Native ad rows: $nativeAdRows")
    }

    private fun numberIsNativeAdPosition(number: Int): Boolean {
        val commonDifference = AdConfig.NATIVE_AD_AFTER_POSTS + 1
        val positionInSequence = ((number + commonDifference) + 1.0) / commonDifference
        return isWholeNumber(positionInSequence)
    }

    private fun isWholeNumber(number: Double): Boolean {
        return number == floor(number) && number.isFinite()
    }
}
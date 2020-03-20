package com.kryptkode.adbase

enum class AdType private constructor(val type: Int) {
    AD_MOB(1),
    FACEBOOK(2);


    companion object {

        fun getType(type: Int): AdType {
            when (type) {
                1 -> return AD_MOB
                2 -> return FACEBOOK
                else -> throw IllegalArgumentException("No type found for $type")
            }
        }
    }
}
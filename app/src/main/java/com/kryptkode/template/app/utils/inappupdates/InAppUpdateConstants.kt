package com.kryptkode.template.app.utils.inappupdates

object InAppUpdateConstants {
    const val UPDATE_ERROR_START_APP_UPDATE_FLEXIBLE = 100
    const val UPDATE_ERROR_START_APP_UPDATE_IMMEDIATE = 101

    enum class UpdateMode {
        FLEXIBLE, IMMEDIATE
    }
}
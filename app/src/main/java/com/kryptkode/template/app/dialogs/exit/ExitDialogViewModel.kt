package com.kryptkode.template.app.dialogs.exit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kryptkode.template.BuildConfig
import com.kryptkode.template.app.data.model.Event
import com.kryptkode.template.app.utils.rating.RatingManager


class ExitDialogViewModel (private val ratingManager: RatingManager) : ViewModel() {


    private val exit = MutableLiveData<Event<Unit>>()
    fun getExit(): LiveData<Event<Unit>> = exit

    private val showRateButton = MutableLiveData<Boolean>()
    fun getShowRateButton(): LiveData<Boolean> = showRateButton

    private val close = MutableLiveData<Event<Unit>>()
    fun getClose(): LiveData<Event<Unit>> = close

    private val openPlayStore = MutableLiveData<Event<Unit>>()
    fun getOpenPlayStore(): LiveData<Event<Unit>> = openPlayStore

    init {
        fetchShowRateButton()
    }

    private fun fetchShowRateButton() {
        ratingManager.monitor()
        ratingManager.setDebug(BuildConfig.DEBUG)
        showRateButton.postValue(ratingManager.showRateIfMeetsConditions())
    }

    fun handleExitClick() {
        exit.postValue(Event(Unit))
    }

    fun handleRateClick() {
        openPlayStore.postValue(Event(Unit))
    }

    fun handleCloseClick() {
        close.postValue(Event(Unit))
    }

}
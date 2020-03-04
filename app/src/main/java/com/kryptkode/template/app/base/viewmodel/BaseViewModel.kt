package com.kryptkode.template.app.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kryptkode.template.app.data.model.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException


abstract class BaseViewModel : ViewModel() {

    protected val loading = MutableLiveData<Event<Boolean>>(Event(false))
    fun getLoadingValueEvent(): LiveData<Event<Boolean>> = loading

    protected val errorMessage = MutableLiveData<Event<String>>()
    fun getErrorMessageEvent(): LiveData<Event<String>> = errorMessage


    protected fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                showLoading()
                block()
                hideLoading()
            } catch (error: Throwable) {
                handleError(error)
                hideLoading()
            } finally {

            }
        }
    }

    private fun handleError(error: Throwable) {
        //Create Error handler
        errorMessage.postValue(
            Event(
                when (error) {
                    is HttpException -> {
                        error.message()
                    }
                    is IOException -> {
                        "Please check your internet connection"
                    }

                    else -> {
                        error.localizedMessage
                    }
                }
            )
        )
        Timber.e(error)
    }

    protected fun hideLoading() {
        loading.postValue(Event(false))
    }

    protected fun showLoading() {
        loading.postValue(Event(true))
    }


}
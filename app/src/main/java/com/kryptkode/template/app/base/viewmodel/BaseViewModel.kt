package com.kryptkode.template.app.base.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.kryptkode.template.app.data.domain.state.DataState
import com.kryptkode.template.app.data.model.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException


abstract class BaseViewModel : ViewModel() {

    protected val loading = MediatorLiveData<Event<Boolean>>()
    fun getLoadingValueEvent(): LiveData<Event<Boolean>> = loading

    protected val errorMessage = MediatorLiveData<Event<String>>()
    fun getErrorMessageEvent(): LiveData<Event<String>> = errorMessage

    private val showMessage = MutableLiveData<Event<Int>>()
    fun getShowMessageEvent(): LiveData<Event<Int>> = showMessage


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

    protected fun handleError(error: Throwable) {
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

    protected fun <T> addErrorSource(data: LiveData<DataState<T>>){
        errorMessage.addSource(data){
            if(it is DataState.Error){
                errorMessage.postValue(Event(it.errorMessage))
            }
        }

    }

    protected fun <T> addLoadingSource(data:LiveData<DataState<T>>){
        loading.addSource(data){
            loading.postValue(Event(it is DataState.Loading))
        }
    }

    protected fun <T> addErrorAndLoadingSource(data: LiveData<DataState<T>>){
        addErrorSource(data)
        addLoadingSource(data)
    }

    protected fun showMessage (@StringRes resId:Int){
        showMessage.postValue(Event(resId))
    }


}
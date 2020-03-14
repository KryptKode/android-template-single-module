package com.kryptkode.template

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.model.Event
import com.kryptkode.template.app.utils.binding.NavigationViewItemClickListener
import timber.log.Timber

/**
 * Created by kryptkode on 3/4/2020.
 */
class MainActivityViewModel : BaseViewModel() {
    private val toggleEndNavDrawer = MutableLiveData<Event<Unit>>()
    fun getToggleEndNavDrawerEvent(): LiveData<Event<Unit>> = toggleEndNavDrawer

    private val toggleStartNavDrawer = MutableLiveData<Event<Unit>>()
    fun getToggleStartNavDrawerEvent(): LiveData<Event<Unit>> = toggleStartNavDrawer

    val goToFeedBack = MutableLiveData<Event<Unit>>()
    val shareApp = MutableLiveData<Event<Unit>>()
    val rateApp = MutableLiveData<Event<Unit>>()
    val goToFavorites = MutableLiveData<Event<Unit>>()
    val goToLicences = MutableLiveData<Event<Unit>>()
    val goToMoreApps = MutableLiveData<Event<Unit>>()
    val goToPrivacyPolicy = MutableLiveData<Event<Unit>>()

    val endNavigationItemListener = object : NavigationViewItemClickListener {
        override fun onItemClick(itemId: Int) {
            toggleEndNavDrawer()
            when (itemId) {
                R.id.action_favorites -> goToFavorites.postValue(Event(Unit))

                R.id.action_rate -> rateApp.postValue(Event(Unit))

                R.id.action_share -> shareApp.postValue(Event(Unit))

                R.id.action_contact -> goToFeedBack.postValue(Event(Unit))

                R.id.action_privacy_policy -> goToPrivacyPolicy.postValue(Event(Unit))

                R.id.action_open_source -> goToLicences.postValue(Event(Unit))


                R.id.action_more_apps -> goToMoreApps.postValue(Event(Unit))

            }
        }
    }

    fun handleMoreOptionsClick() {
        toggleEndNavDrawer()
    }

    fun handleEndNavCloseClick() {
        toggleEndNavDrawer()
    }

    fun handleStartNavCloseClick() {
        Timber.d("handleStartNavCloseClick")
        toggleStartNavDrawer()
    }

    private fun toggleEndNavDrawer() {
        toggleEndNavDrawer.postValue(Event(Unit))
    }

    private fun toggleStartNavDrawer() {
        toggleStartNavDrawer.postValue(Event(Unit))
    }

    fun sendFeedBack(subject: String?, message: String?) {
        Timber.d("Fake sending feedback: $subject - $message")
    }

}
package com.kryptkode.template.carddetails

import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import coil.Coil
import coil.api.get
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CardRepository
import com.kryptkode.template.app.data.domain.state.successOr
import com.kryptkode.template.app.data.model.Event
import com.kryptkode.template.app.utils.ImageUrl
import com.kryptkode.template.cardlist.mapper.CardViewMapper
import com.kryptkode.template.cardlist.model.CardForView
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by kryptkode on 3/14/2020.
 */
class CardDetailViewModel(
    private val cardRepository: CardRepository,
    private val cardViewMapper: CardViewMapper
) : BaseViewModel() {

    private val defaultCard = MutableLiveData<CardForView>()

    val cardList = defaultCard.switchMap {
        val result  = cardRepository.getCardsForSubcategory(it.subcategoryId)
        addErrorAndLoadingSource(result)
        result.map {
            it.successOr(listOf()).map {
                cardViewMapper.mapTo(it)
            }
        }
    }

    private val shareOthers = MutableLiveData<Event<File>>()
    fun getShareOthersState(): LiveData<Event<File>> = shareOthers

    private val shareWhatsApp = MutableLiveData<Event<File>>()
    fun getShareWhatsAppState(): LiveData<Event<File>> = shareWhatsApp

    private val shareTwitter = MutableLiveData<Event<File>>()
    fun getShareTwitterState(): LiveData<Event<File>> = shareTwitter

    private val shareFacebook = MutableLiveData<Event<File>>()
    fun getShareFacebookState(): LiveData<Event<File>> = shareFacebook

    private val askForStoragePermission = MutableLiveData<Event<Unit>>()
    fun getAskForStoragePermissionEvent(): LiveData<Event<Unit>> = askForStoragePermission

    private lateinit var clickedCard: CardForView
    private lateinit var shareType: ShareType

    fun onShareWhatsApp(cardForView: CardForView?) {
        saveImageAndShare(cardForView, ShareType.WHATS_APP)
    }

    fun onShareTwitter(cardForView: CardForView?) {
        saveImageAndShare(cardForView, ShareType.TWITTER)
    }

    fun onShareFacebook(cardForView: CardForView?) {
        saveImageAndShare(cardForView, ShareType.FACEBOOK)
    }

    fun onShareOtherApps(cardForView: CardForView?) {
        saveImageAndShare(cardForView, ShareType.OTHERS)
    }

    private fun saveImageAndShare(
        cardForView: CardForView?,
        shareType: ShareType
    ) {
        this.clickedCard = cardForView!!
        this.shareType = shareType
        checkStoragePermission()
    }

    private fun checkStoragePermission() {
        askForStoragePermission.postValue(Event(Unit))
    }

    fun onPermissionStorageGranted(rootDir: File?) {
        launchDataLoad {
            val drawable = Coil.get(ImageUrl.getImageUrl(clickedCard.imageUrl))
            val bitmap = drawable.toBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
            val savedFile = saveBitmapToFile(rootDir, getFileName(), bitmap)
            savedFile?.let {
                shareFileBasedOnType(savedFile)
            }
        }
    }

    private fun shareFileBasedOnType(savedFile: File) {
        when(shareType){
            ShareType.WHATS_APP -> {
                shareWhatsApp.postValue(Event(savedFile))
            }

            ShareType.FACEBOOK -> {
                shareFacebook.postValue(Event(savedFile))
            }

            ShareType.TWITTER -> {
                shareTwitter.postValue(Event(savedFile))
            }

            else -> {
                shareOthers.postValue(Event(savedFile))
            }
        }
    }

    private fun getFileName(): String {
        return "${clickedCard.name}.png"
    }

    private fun saveBitmapToFile(rootDir: File?, fileName: String, bitmap: Bitmap): File? {
        var fos: FileOutputStream? = null
        try {
        val imageFile = File(rootDir, fileName)
        if(imageFile.exists()){
            return imageFile
        }
            fos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
            return imageFile
        } catch (e: IOException) {
            Timber.e(e)
            errorMessage.postValue(Event("Error occurred while saving image... Try again"))
            if (fos != null) {
                try {
                    fos.close()
                } catch (e1: IOException) {
                    Timber.e(e1)
                }
            }
        }
        return null
    }

    fun toggleFavorite(item: CardForView?, favorite: Boolean) {
        launchDataLoad {
            item?.let {
                if (favorite) {
                    cardRepository.markCardAsFavorite(cardViewMapper.mapFrom(item))
                } else {
                    cardRepository.unMarkCardAsFavorite(cardViewMapper.mapFrom(item))
                }
            }
        }
    }

    fun loadData(card: CardForView) {
        this.defaultCard.postValue(card)
    }


    enum class ShareType {
        WHATS_APP, FACEBOOK, TWITTER, OTHERS
    }


}
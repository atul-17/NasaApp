package com.nasaApp.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nasaApp.models.ModelNasaAppDataItem
import com.nasaApp.repository.NasaRepository


class NasaDataViewModel(application: Application) : AndroidViewModel(application) {

    private var nasaDataItemsObservableList: MutableLiveData<MutableList<ModelNasaAppDataItem>>? =
        null

    init {
        nasaDataItemsObservableList = NasaRepository().getNasaAppLiveDataItems()
    }

    /**
     * Expose the LiveData nasa query so the UI can observe it.
     */
    fun getNasaDataItems(): MutableLiveData<MutableList<ModelNasaAppDataItem>>? {
        Log.d("tag","called_NasaDataViewModel")
        return nasaDataItemsObservableList
    }
}
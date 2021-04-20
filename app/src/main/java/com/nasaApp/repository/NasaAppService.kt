package com.nasaApp.repository

import com.nasaApp.models.ModelNasaAppDataItem
import retrofit2.Call
import retrofit2.http.GET

interface NasaAppService {

    @GET("b/60681cc79fc4de52061cc9ea/5")
    fun getNasaAppDataItems(): Call<MutableList<ModelNasaAppDataItem>>?
}
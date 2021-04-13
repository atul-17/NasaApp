package com.nasaApp.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nasaApp.R
import com.nasaApp.adapter.NasaAppGridViewAdapter
import com.nasaApp.models.ModelNasaAppDataItem
import com.nasaApp.viewModels.NasaDataViewModel
import kotlinx.android.synthetic.main.nasa_app_grid_layout.*


var modelNasaAppDataItemList: MutableList<ModelNasaAppDataItem>? = ArrayList()

var nasaAppGridViewAdapter: NasaAppGridViewAdapter? = null

var nasaDataViewModel: NasaDataViewModel? = null


class NasaAppGridActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
//        setContentView(R.layout.nasa_app_grid_layout)

        nasaDataViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                NasaDataViewModel::class.java
            )


        getNasaDataItemsApi()
    }


    fun getNasaDataItemsApi() {
        nasaDataViewModel?.getNasaDataItems()?.observe(this, Observer { it ->
            modelNasaAppDataItemList
            if (modelNasaAppDataItemList != null) {
                setAdapter(modelNasaAppDataItemList.let { it -> ArrayList() })
            }
        })

    }

    fun setAdapter(modelNasaAppDataItemList: MutableList<ModelNasaAppDataItem>) {
        nasaAppGridViewAdapter =
            NasaAppGridViewAdapter(this@NasaAppGridActivity, modelNasaAppDataItemList)
        nasaRecyclerView.adapter = nasaAppGridViewAdapter
    }

}
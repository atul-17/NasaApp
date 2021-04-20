package com.nasaApp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nasaApp.R
import com.nasaApp.adapter.NasaAppGridViewAdapter
import com.nasaApp.models.ModelNasaAppDataItem
import com.nasaApp.repository.NasaAppService
import com.nasaApp.utils.ImageOnclickListnerInterface
import com.nasaApp.utils.RecyclerItemClickListener
import com.nasaApp.viewModels.NasaDataViewModel
import kotlinx.android.synthetic.main.nasa_app_grid_layout_activity.*


var modelNasaAppDataItemList: MutableList<ModelNasaAppDataItem>? = ArrayList()

var nasaAppGridViewAdapter: NasaAppGridViewAdapter? = null

var nasaDataViewModel: NasaDataViewModel? = null


class NasaAppGridActivity : AppCompatActivity(), ImageOnclickListnerInterface {

    private var TAG = NasaAppGridActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nasa_app_grid_layout_activity)

        nasaDataViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                NasaDataViewModel::class.java
            )

        Log.d("tag", "called_onCreate")

        showProgressBar()
        getNasaDataItemsApi()

//        nasaRecyclerView.addOnItemTouchListener(
//            RecyclerItemClickListener(
//                this@NasaAppGridActivity,
//                nasaRecyclerView,
//                object : RecyclerItemClickListener.OnItemClickListener {
//                    override fun onItemClick(view: View?, position: Int) {
//
//                    }
//
//                    override fun onLongItemClick(view: View?, position: Int) {
//
//                    }
//                })
//        )

    }


    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun closeProgressBar() {
        progressBar.visibility = View.GONE
    }

    fun getNasaDataItemsApi() {

        nasaDataViewModel?.getNasaDataItems()!!.observe(this, Observer {
            it
            modelNasaAppDataItemList = it
            Log.d("tag", "called_getNasaDataItemsApi: ".plus(modelNasaAppDataItemList?.size))
            closeProgressBar()
            setAdapter(modelNasaAppDataItemList!!)
        })

    }

    fun setAdapter(modelNasaAppDataItemList: MutableList<ModelNasaAppDataItem>) {

        nasaAppGridViewAdapter =
            NasaAppGridViewAdapter(this@NasaAppGridActivity, modelNasaAppDataItemList)

        nasaAppGridViewAdapter?.imageOnclickListnerInterface = this


        nasaRecyclerView.layoutManager =
            LinearLayoutManager(this)

        nasaRecyclerView.adapter = nasaAppGridViewAdapter


    }

    override fun onClick(itemView: View, position: Int) {
        val intent =
            Intent(this@NasaAppGridActivity, NasaAppDetailsActivity::class.java)
        val bundle = Bundle()


        val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this@NasaAppGridActivity as AppCompatActivity,
            itemView,
            itemView.transitionName
        )

        bundle.putSerializable(
            "nasaImageDetails",
            modelNasaAppDataItemList?.get(position)
        )
        intent.putExtras(bundle)
        startActivity(intent, activityOptionsCompat.toBundle())
    }

}
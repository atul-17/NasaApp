package com.nasaApp.activities

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.nasaApp.R
import com.nasaApp.models.ModelNasaAppDataItem
import com.nasaApp.utils.GlideApp
import kotlinx.android.synthetic.main.nasa_app_details_layout_activity.*


class NasaAppDetailsActivity : AppCompatActivity() {

    var bundle = Bundle()

    var modelNasaAppDataItem: ModelNasaAppDataItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nasa_app_details_layout_activity)
        bundle = intent.extras!!
        modelNasaAppDataItem = bundle.getSerializable("nasaImageDetails") as ModelNasaAppDataItem?

        setImageDetails()
    }

    fun setImageDetails() {


        GlideApp.with(this@NasaAppDetailsActivity).load(modelNasaAppDataItem?.hdurl)
            .fitCenter()
            .centerCrop()
            .dontTransform()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(ivNasaPic);

        supportPostponeEnterTransition();

        ivNasaPic.getViewTreeObserver().addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    ivNasaPic.getViewTreeObserver().removeOnPreDrawListener(this)
                    supportStartPostponedEnterTransition()
                    return true
                }
            }
        )

        tvTitle.text = modelNasaAppDataItem?.title

        tvDate.text = modelNasaAppDataItem?.date

        var copyright = modelNasaAppDataItem?.copyright ?: "NoData"

        if (copyright != "NoData") {
            llCopyright.visibility = View.VISIBLE
            tvCopyright.text = modelNasaAppDataItem?.copyright
        } else {
            llCopyright.visibility = View.GONE
        }


        tvExplanation.text = modelNasaAppDataItem?.explanation
    }

}
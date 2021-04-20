package com.nasaApp.adapter


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.nasaApp.R
import com.nasaApp.activities.NasaAppDetailsActivity
import com.nasaApp.models.ModelNasaAppDataItem
import com.nasaApp.utils.GlideApp
import com.nasaApp.utils.ImageOnclickListnerInterface


class NasaAppGridViewAdapter internal constructor(
    val context: Context,
    val nasaDataItemsList: MutableList<ModelNasaAppDataItem>
) :
    RecyclerView.Adapter<NasaAppGridViewAdapter.NasaDataViewHolder>() {


    var imageOnclickListnerInterface: ImageOnclickListnerInterface? = null


    inner class NasaDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivNasaPic: RoundedImageView = itemView.findViewById(R.id.ivNasaPic)


        fun setPostImage(modelNasaAppDataItem: ModelNasaAppDataItem) {


            GlideApp.with(context).load(modelNasaAppDataItem.url)
                .fitCenter()
                .centerCrop()
                .dontTransform()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(ivNasaPic);

            ivNasaPic.setOnClickListener {
                imageOnclickListnerInterface?.onClick(ivNasaPic, adapterPosition)
            }

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NasaDataViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.nasa_app_grid_view_adapter, parent, false)
        return NasaDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: NasaDataViewHolder, position: Int) {
        holder.setPostImage(nasaDataItemsList[position])

    }

    override fun getItemCount(): Int {
        Log.d("tag", "getItemCount")
        return nasaDataItemsList.size
    }

}
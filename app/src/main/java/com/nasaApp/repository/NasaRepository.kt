package com.nasaApp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nasaApp.models.ModelNasaAppDataItem
import com.nasaApp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NasaRepository {
    private var nasaAppService: NasaAppService? = null


    private var TAG = NasaRepository::class.java.simpleName


    fun getNasaAppLiveDataItems(): MutableLiveData<MutableList<ModelNasaAppDataItem>> {

        val dataItemList: MutableLiveData<MutableList<ModelNasaAppDataItem>> =
            MutableLiveData()


        nasaAppService = getService()


        nasaAppService?.getNasaAppDataItems()?.enqueue(object :
            retrofit2.Callback<MutableList<ModelNasaAppDataItem>?> {
            override fun onResponse(
                call: Call<MutableList<ModelNasaAppDataItem>?>,
                response: Response<MutableList<ModelNasaAppDataItem>?>
            ) {
                Log.d(TAG, "retrofit_data:".plus(response.body()?.get(0)?.hdurl))
                dataItemList.value = response.body();
            }

            override fun onFailure(
                call: Call<MutableList<ModelNasaAppDataItem>?>,
                t: Throwable
            ) {
                Log.d(TAG, "retrofit_exception: ".plus(t.toString()))
            }

        })
        return dataItemList
    }


    private fun getHttpClient(): OkHttpClient {
        val okClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original: Request = chain.request()

                // Request customization: add request headers
                val requestBuilder: Request.Builder = original.newBuilder()
                    .header("secret-key", "$2b$10$8lwPT5INLgdrwNvHv/dv1uPPEF9Rvi627cIJC84xnLsjh4kcsIb5S")
                    .method(original.method(), original.body())
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
        return okClient
    }

    private fun getService(): NasaAppService? {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants().BASEURL)
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create()) //assuming JSON
            .build()
        return retrofit.create(NasaAppService::class.java)
    }

}
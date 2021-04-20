package com.nasaApp.models

import java.io.Serializable

data class ModelNasaAppDataItem( var copyright: String?,
                                 var date: String?, var explanation: String?,
                                 var hdurl: String?, var media_type: String?,
                                 var service_version: String?,
                                 var title: String?,
                                 var url: String?) : Serializable




package com.example.buildingrecognition.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * used for
 * style by imurluck
 * style at 2019-08-11
 */

data class Recognition(
    val buildStyle: String,
    @SerializedName("styleinfo")
    val styleInfo: String?,
<<<<<<< HEAD
    @SerializedName("buildings")
    val buildings: List<Building>?
) : Serializable
=======
    val buildings: List<Building>?
)
>>>>>>> 7c449861c9db611775ba8bfac5266c06673d5f8f

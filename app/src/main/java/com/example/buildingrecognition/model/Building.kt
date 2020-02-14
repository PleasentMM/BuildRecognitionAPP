package com.example.buildingrecognition.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * used for
 * style by imurluck
 * style at 2019-08-11
 */

data class Building(

    val address: String?,
    val created: String?,
    val houseNumber: String?,
    val description: String?,
    val designer: List<Designer>?,
    val relation: List<Designer>?,
    val uri: String?,
    val architecturalStyle: String?,
    val hasFile: String?,
    val name: String?,
    val architectureStructure: String?,
    @SerializedName("yname")
    val yName: List<YName>?,
    val location: List<Location>?,
    val lane: List<Lane>?,
    val event: List<Event>

) : Serializable
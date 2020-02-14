package com.example.buildingrecognition.model

import java.io.Serializable

/**
 * used for
 * style by imurluck
 * style at 2019-08-11
 */
data class Lane(
    private val road: String?,
    private val label: String?
) : Serializable
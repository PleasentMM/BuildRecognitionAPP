package com.example.buildingrecognition.constant

import android.content.Context
import com.example.buildingrecognition.R
import java.io.File

/**
 * used for
 * style by imurluck
 * style at 2019-08-09
 */


internal const val KEY_ARG_PHOTO_URL = "key_arg_photo_url"


const val KEY_EVENT_ACTION = "key_event_action"
const val KEY_EVENT_EXTRA = "key_event_extra"
const val KEY_BUILDING = "key_building"

private const val IMMERSIVE_FLAG_TIMEOUT = 500L

/** Use external media if it is available, our app's file directory otherwise */
internal fun getOutputDirectory(context: Context): File {
    val appContext = context.applicationContext
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() } }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else appContext.filesDir
}
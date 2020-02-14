package com.example.buildingrecognition

<<<<<<< HEAD
import com.example.buildingrecognition.model.Building
=======
>>>>>>> 7c449861c9db611775ba8bfac5266c06673d5f8f
import com.example.buildingrecognition.model.Recognition
import com.imurluck.net.HttpHelper
import com.imurluck.net.HttpUtil
import com.imurluck.net.LoadCallback
import com.imurluck.net.common.Constant

/**
 * used for
 * style by imurluck
 * style at 2019-08-11
 */
object RecognitionRepository {

    private const val TAG_RECOGNITION = "recognition"

    private const val KEY_PHOTO_FILE = "document"

    fun recognition(photoUrl: String, loadCallback: LoadCallback<Recognition>) {
        HttpHelper.getInstance().post(Constant.URL_RECOGNITION, HashMap(),
            loadCallback, TAG_RECOGNITION, HttpUtil.createFilePart(KEY_PHOTO_FILE, photoUrl))
    }
}
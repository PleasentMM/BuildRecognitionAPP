package com.example.buildingrecognition.widget

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.buildingrecognition.R
import com.example.buildingrecognition.RecognitionRepository
import com.example.buildingrecognition.buildinglist.BuildingListActivity
import com.example.buildingrecognition.constant.KEY_ARG_PHOTO_URL
import com.example.buildingrecognition.model.Recognition
import com.imurluck.net.LoadCallback
import kotlinx.android.synthetic.main.dialog_recognition.*

/**
 * used for
 * style by imurluck
 * style at 2019-08-11
 */
class RecognitionDialogFragment : DialogFragment() {

    private lateinit var photoUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            photoUrl = getString(KEY_ARG_PHOTO_URL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
                    = inflater.inflate(R.layout.dialog_recognition, container, false).apply {
        setupWindowAttributes(dialog!!)
    }


    private fun setupWindowAttributes(dialog: Dialog) {
        dialog.setCanceledOnTouchOutside(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawableResource(R.drawable.bg_recognition_dialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recognitionStatusTv.onFailedClick = {
            shownImg.startScanAnimation()
            startRecognition()
        }

        Glide.with(context!!)
            .load(photoUrl)
            .into(shownImg)

        startRecognition()


    }

    fun start(data : Recognition) {
        var intent = Intent(activity,BuildingListActivity::class.java)
        intent.putExtra("BuildingList",data)
        startActivity(intent)
    }
    /**
     * 将图片上传到后端进行识别
     */
    private fun startRecognition() {
        recognitionStatusTv.recognising()
        RecognitionRepository.recognition(photoUrl, object : LoadCallback<Recognition> {
            override fun loadSuccess(data: Recognition) {
                shownImg.cancelScanAnimation()
                recognitionStatusTv.recogniseSuccess(data.buildStyle)
<<<<<<< HEAD
                if (data != null) {
                    recognitionStatusTv.setOnClickListener { start(data) }
                } else {
                    recognitionStatusTv.recogniseFailed()
                }
=======
>>>>>>> 7c449861c9db611775ba8bfac5266c06673d5f8f
            }

            override fun loadFailed(errorMessage: String) {
                shownImg.cancelScanAnimation()
                recognitionStatusTv.recogniseFailed()
            }

            override fun loadNull(message: String) {

            }

        })
    }

    companion object {

        private const val TAG = "RecognitionDialogFragment"

        fun show(photoUrl: String, fragmentManager: FragmentManager) {
            RecognitionDialogFragment().apply {
                arguments = Bundle().apply { putString(KEY_ARG_PHOTO_URL, photoUrl) }
            }.show(fragmentManager, TAG)
        }
    }

}
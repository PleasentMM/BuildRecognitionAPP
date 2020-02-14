package com.example.buildingrecognition.recognition

import android.os.Bundle
import com.example.buildingrecognition.CameraFragment
import com.example.buildingrecognition.R
import com.example.buildingrecognition.base.BaseActivity

class RecognitionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recognition)

        replaceCameraFragment()
    }

    private fun replaceCameraFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CameraFragment())
            .commit()
    }
}

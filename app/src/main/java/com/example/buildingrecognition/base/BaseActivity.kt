package com.example.buildingrecognition.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 * used for
 * style by imurluck
 * style at 2019-08-10
 */
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
    }

    private fun transparentStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                val decorClazz = Class.forName("com.android.internal.policy.DecorView")
                val field = decorClazz.getDeclaredField("mSemiTransparentStatusBarColor")
                field.isAccessible = true
                field.set(window.decorView, Color.TRANSPARENT)
            } catch (e: Exception) {

            }
        }
    }
}
package com.example.buildingrecognition.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.IntDef
import com.example.buildingrecognition.R
import com.example.buildingrecognition.model.Building
import com.example.buildingrecognition.model.Recognition

/**
 * used for
 * style by imurluck
 * style at 2019-08-11
 */
class StatusTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyleInt: Int = 0
): TextView(context, attributeSet, defaultStyleInt) {

    var onFailedClick: (() -> Unit)? = null

    private val TEXT_RECOGNITION_FAILED = resources.getString(R.string.recognition_status_failed)
    private val TEXT_RECOGNITION_ING = resources.getString(R.string.recognition_status_ing)

    private val statusChangeAnimation = AnimatorSet().apply {
        duration = DURATION
        val hideAnimator = AnimatorSet().apply {
            playTogether(ObjectAnimator.ofFloat(this@StatusTextView, View.SCALE_X, scaleX, 0.0F),
                ObjectAnimator.ofFloat(this@StatusTextView, View.SCALE_Y, scaleY, 0.0F),
                ObjectAnimator.ofFloat(this@StatusTextView, View.ALPHA, alpha, 0.0F))
        }
        val showAnimator = AnimatorSet().apply {
            playTogether(ObjectAnimator.ofFloat(this@StatusTextView, View.SCALE_X, scaleX, 1.0F),
                ObjectAnimator.ofFloat(this@StatusTextView, View.SCALE_Y, scaleY, 1.0F),
                ObjectAnimator.ofFloat(this@StatusTextView, View.ALPHA, alpha, 1.0F))
        }
        playSequentially(hideAnimator, showAnimator)
    }

    @RecognitionStatus private var status: Int = STATUS_RECOGNISING

    fun recognising() {
        status = STATUS_RECOGNISING
        changeStatus(status, TEXT_RECOGNITION_ING)
    }

    fun recogniseSuccess(text: String) {
        status = STATUS_RECOGNISE_SUCCESS
        changeStatus(status, text)
    }

    fun recogniseFailed() {
        status = STATUS_RECOGNISE_FAILED
        changeStatus(status, TEXT_RECOGNITION_FAILED)
    }

    private fun changeStatus(@RecognitionStatus status: Int, text: String) {

        if (status == STATUS_RECOGNISE_FAILED) {
            setOnClickListener {
                onFailedClick?.invoke()
            }
        } else {
            setOnClickListener(null)
        }
        setText(text)
        if (!statusChangeAnimation.isRunning) {
            statusChangeAnimation.start()
        }
    }

    @IntDef(STATUS_RECOGNISING, STATUS_RECOGNISE_SUCCESS, STATUS_RECOGNISE_FAILED)
    @Retention(AnnotationRetention.SOURCE)
    annotation class RecognitionStatus

    companion object {

        private const val STATUS_RECOGNISING = 1
        private const val STATUS_RECOGNISE_SUCCESS = 2
        private const val STATUS_RECOGNISE_FAILED = 3

        private const val DURATION = 300L
    }
}
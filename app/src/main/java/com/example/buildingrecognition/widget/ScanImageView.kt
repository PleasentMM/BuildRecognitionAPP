package com.example.buildingrecognition.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.buildingrecognition.R

/**
 * used for
 * style by imurluck
 * style at 2019-08-11
 */
class ScanImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyleInt: Int = 0
): ImageView(context, attributeSet, defaultStyleInt) {

    private var duration = DEFAULT_DURATION

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var scanAnimation: ValueAnimator? = null

    private var startX = 0F
    private var startY = 0F
    private var endX = 0F
    private var endY = 0F

    init {
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = resources.getDimensionPixelOffset(R.dimen.scan_line_stroke_width).toFloat()
        linePaint.color = ContextCompat.getColor(context, android.R.color.white)
        post {
            scanAnimation = ValueAnimator.ofFloat(0F, width.toFloat()).apply {
                duration = this@ScanImageView.duration
                addUpdateListener {
                    startX = it.animatedValue as Float
                    endX = startX
                    invalidate()
                }
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                start()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        startX = 0F
        startY = 0F
        endX = 0F
        endY = h.toFloat()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelScanAnimation()
    }

    fun cancelScanAnimation() {
        scanAnimation?.cancel()
    }

    fun startScanAnimation() {
        scanAnimation?.start()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawLine(startX, startY, endX, endY, linePaint)
    }

    companion object {
        private const val DEFAULT_DURATION = 5000L
    }
}
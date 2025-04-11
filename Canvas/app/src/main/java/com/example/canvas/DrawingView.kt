package com.example.canvas

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val path = Path()
    private var bitmap: Bitmap? = null
    private val paths = mutableListOf<Pair<Path, Paint>>()

    init {
        paint.color = Color.BLACK
        paint.isAntiAlias = true
        paint.strokeWidth = 20f
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }

        for ((p, paint) in paths) {
            canvas.drawPath(p, paint)
        }

        canvas.drawPath(path, paint)
    }

    fun setColor(color: Int) {
        paint.color = color
    }

    fun setBrushSize(size: Float) {
        paint.strokeWidth = size
    }

    fun clearCanvas() {
        paths.clear()
        path.reset()
        invalidate()
    }

    fun setBackgroundBitmap(newBitmap: Bitmap) {
        bitmap = newBitmap
        invalidate()
    }

    fun getBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                path.moveTo(x, y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
            }
            MotionEvent.ACTION_UP -> {
                paths.add(Pair(Path(path), Paint(paint)))
                path.reset()
            }
        }

        invalidate()
        return true
    }
}

package com.example.canvas

import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import android.content.Intent


class MainActivity : AppCompatActivity() {

    private lateinit var drawingView: DrawingView
    private lateinit var loadImageLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawingView)

        findViewById<Button>(R.id.clearButton).setOnClickListener {
            drawingView.clearCanvas()
        }

        findViewById<Button>(R.id.blueButton).setOnClickListener {
            drawingView.setColor(Color.parseColor("#2196F3"))
        }

        findViewById<Button>(R.id.redButton).setOnClickListener {
            drawingView.setColor(Color.parseColor("#F44336"))
        }

        findViewById<Button>(R.id.greenButton).setOnClickListener {
            drawingView.setColor(Color.parseColor("#4CAF50"))
        }

        findViewById<Button>(R.id.purpleButton).setOnClickListener {
            drawingView.setColor(Color.parseColor("#9C27B0"))
        }

        findViewById<Button>(R.id.orangeButton).setOnClickListener {
            drawingView.setColor(Color.parseColor("#FF9800"))
        }

        findViewById<Button>(R.id.blackButton).setOnClickListener {
            drawingView.setColor(Color.parseColor("#000000"))
        }

        val brushSeekBar = findViewById<SeekBar>(R.id.brushSizeSeekBar)
        brushSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val brushSize = progress.toFloat().coerceAtLeast(1f)
                drawingView.setBrushSize(brushSize)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        loadImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val selectedImage = result.data?.data
                selectedImage?.let { uri ->
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    drawingView.setBackgroundBitmap(bitmap)
                }
            }
        }

        findViewById<Button>(R.id.loadImageButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            loadImageLauncher.launch(intent)
        }
    }




}

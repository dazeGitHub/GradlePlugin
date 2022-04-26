package com.hencoder.testgradle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zyz.testgradle.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawBadge()
    }
}
package com.hencoder.testgradle

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup

fun Activity.drawBadge(){
    val badge = View(this)
    badge.setBackgroundColor(Color.GREEN)
    val decorView = window.decorView as ViewGroup
    decorView.addView(badge, 200, 200)
}

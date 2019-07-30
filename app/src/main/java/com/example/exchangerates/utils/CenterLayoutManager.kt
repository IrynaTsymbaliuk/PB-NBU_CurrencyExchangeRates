package com.example.exchangerates.utils

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class CenterLayoutManager(context: Context) : LinearLayoutManager(context) {
    private var smoothScroller: CenterSmoothScroller? = null

    init {
        smoothScroller = CenterSmoothScroller(context)
    }

    companion object {
        private const val MILLISECONDS_PER_INCH = 150f
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        smoothScroller?.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    private class CenterSmoothScroller internal constructor(context: Context) : LinearSmoothScroller(context) {

        override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
            return boxStart - viewStart
        }

        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
            return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
        }
    }
}
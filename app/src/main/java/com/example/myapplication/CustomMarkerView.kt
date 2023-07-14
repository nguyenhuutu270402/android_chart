package com.example.myapplication


import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class CustomMarkerView(context: Context, layoutResource: Int, private val entries: List<Entry>) :
    MarkerView(context, layoutResource) {
    private val tvValue1: TextView = findViewById(R.id.tvValue1)
    private val tvValue2: TextView = findViewById(R.id.tvValue2)

    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        if (entry != null) {
            if (entry.x < entries.size - 5) {
                tvValue1.text = ""
                tvValue2.text = entry.y.toString()

            } else {
                tvValue1.text = entry.y.toString()
                tvValue2.text = ""
            }
        }

        super.refreshContent(entry, highlight)
    }

    override fun getOffsetForDrawingAtPoint(xpos: Float, ypos: Float): MPPointF {
        return MPPointF(-width / 2f, -height / 2f)
    }
}

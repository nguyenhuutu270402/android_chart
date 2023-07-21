package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.Orientation
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var lineChart: LineChart
    private lateinit var btnAdd: Button
    private var entries = ArrayList<Entry>()
    private var entries2 = ArrayList<Entry>()
    private var entries3 = ArrayList<Entry>()
    private var lastEntries = ArrayList<Entry>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lineChart = findViewById(R.id.lineChart)
        btnAdd = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener {
            addItem()

        }
        setupLineChart()
    }


    private fun addItem() {
        val random = Random()
        val x = entries.size.toFloat()
        entries.add(Entry(x, 3f + (random.nextFloat() * (10f - 0f))))
        entries2.add(Entry(x, 3f + (random.nextFloat() * (10f - 0f))))
        entries3.add(Entry(x, 3f + (random.nextFloat() * (10f - 0f))))

        updateLineChart()

        var maxY = Float.MIN_VALUE
        var maxEntryIndex = -1
        for (i in lastEntries.indices) {
            val currentY = lastEntries[i].y
            if (currentY > maxY) {
                maxY = currentY
                maxEntryIndex = i
            }
        }

        // Hiển thị hiệu ứng đánh dấu tại điểm có giá trị y cao nhất
        if (maxEntryIndex >= 0) {
            // Hiển thị hiệu ứng đánh dấu tại điểm có giá trị y cao nhất
            val markerView = CustomMarkerView(this, R.layout.custom_marker_view, entries)
            markerView.refreshContent(lastEntries[maxEntryIndex], Highlight(0f, maxEntryIndex, 0))
            lineChart.marker = markerView
            lineChart.highlightValue(lastEntries[maxEntryIndex].x, 0, false)
        } else {
            // Nếu không có entry nào trong danh sách, giấu hiệu ứng đánh dấu
            lineChart.marker = null
        }
    }

    private fun setupLineChart() {
        lineChart.apply {
            setTouchEnabled(true)
            setPinchZoom(false)
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.isEnabled = false
            legend.isEnabled = false
            description.isEnabled = false
            isDoubleTapToZoomEnabled = false
        }
//        val markerView = CustomMarkerView(this, R.layout.custom_marker_view, entries)
//        lineChart.marker = markerView

        entries.apply {
            add(Entry(0f, 0f))
            add(Entry(1f, 5f))
            add(Entry(2f, 2f))
        }

        entries2.apply {
            add(Entry(0f, 0f))
            add(Entry(1f, 5f))
            add(Entry(2f, 2f))
        }

        entries3.apply {
            add(Entry(0f, 0f))
            add(Entry(1f, 5f))
            add(Entry(2f, 2f))
        }
        updateLineChart()
    }

    private fun updateLineChart() {
        lastEntries = entries.takeLast(10) as ArrayList<Entry>
        val lastEntries2 = entries2.takeLast(20)
        val lastEntries3 = entries3.takeLast(20)

        val dataSet = LineDataSet(lastEntries, "Cubic Line")
        val dataSet2 = LineDataSet(lastEntries2, "Second Line")
        val dataSet3 = LineDataSet(lastEntries3, "Third Line")

        dataSet.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawFilled(false)
            setDrawCircles(false)
            lineWidth = 3f
            circleRadius = 30f
            valueTextColor = Color.TRANSPARENT
            highLightColor = Color.TRANSPARENT
            color = ContextCompat.getColor(this@MainActivity, R.color.my_color_04A0FE)
        }

//        dataSet2.apply {
//            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
//            setDrawFilled(false)
//            setDrawCircles(false)
//            lineWidth = 3f
//            circleRadius = 30f
//            valueTextColor = Color.TRANSPARENT
//            highLightColor = Color.TRANSPARENT
//            color = ContextCompat.getColor(this@MainActivity, R.color.purple_200)
//        }
//
//        dataSet3.apply {
//            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
//            setDrawFilled(false)
//            setDrawCircles(false)
//            lineWidth = 3f
//            circleRadius = 30f
//            valueTextColor = Color.TRANSPARENT
//            highLightColor = Color.TRANSPARENT
//            color = ContextCompat.getColor(this@MainActivity, R.color.purple_500)
//        }

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(dataSet)
        dataSets.add(dataSet2)
        dataSets.add(dataSet3)

        val lineData = LineData(dataSets)
        lineChart.data = lineData
        lineChart.notifyDataSetChanged()
        lineChart.invalidate()
    }

}

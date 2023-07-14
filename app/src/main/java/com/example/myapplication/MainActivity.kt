package com.example.myapplication

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.Orientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var lineChart: LineChart
    private lateinit var btnAdd: Button
    private var entries = ArrayList<Entry>()
    private lateinit var lineData: LineData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lineChart = findViewById(R.id.lineChart)
        btnAdd = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener {
            addItem()
        }

        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

        entries.add(Entry(0f, 0f))
        entries.add(Entry(1f, 4f))
        entries.add(Entry(2f, 4f))
        entries.add(Entry(3f, 4f))
        entries.add(Entry(4f, 4f))
        entries.add(Entry(5f, 14f))
        generateRandomEntries(10)


        val dataSet = LineDataSet(entries, "Cubic Line")
        val markerView = CustomMarkerView(this, R.layout.custom_marker_view)
        lineChart.marker = markerView
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.cubicIntensity = 0.2f
        dataSet.setDrawFilled(true)
        dataSet.setDrawCircles(false)
        dataSet.lineWidth = 1.8f
        dataSet.circleRadius = 30f
        dataSet.valueTextColor = Color.TRANSPARENT
        dataSet.highLightColor = Color.TRANSPARENT
        dataSet.color = ContextCompat.getColor(this, R.color.my_color_04A0FE)

        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.my_color),
            ContextCompat.getColor(this, R.color.my_color_0)
        )
        val gradientDrawable = GradientDrawable(Orientation.TOP_BOTTOM, colors)
        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        dataSet.fillDrawable = gradientDrawable

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(dataSet)

        lineData = LineData(dataSets)
        lineChart.data = lineData
        lineChart.invalidate()

        lineChart.axisLeft.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.description.isEnabled = false
    }


    private fun generateRandomEntries(count: Int) {
        val random = Random()
        for (i in 6 until count) {
            val x = i.toFloat()
            val y = 3f + (random.nextFloat() * (4f - 3f)) // Generate random y value between 3 and 4
            entries.add(Entry(x, y))
        }
    }

    private fun addItem() {
        val random = Random()
        val x = entries.size
        val y = 3f + (random.nextFloat() * (4f - 3f)) // Generate random y value between 3 and 4
        entries.add(Entry(x.toFloat(), y))

        // Update the LineDataSet with the new data points
        val dataSet = LineDataSet(entries, "Cubic Line")
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.cubicIntensity = 0.2f
        dataSet.setDrawFilled(true)
        dataSet.setDrawCircles(false)
        dataSet.lineWidth = 1.8f
        dataSet.circleRadius = 30f
        dataSet.valueTextColor = Color.TRANSPARENT
        dataSet.highLightColor = Color.TRANSPARENT
        dataSet.color = ContextCompat.getColor(this, R.color.my_color_04A0FE)

        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.my_color),
            ContextCompat.getColor(this, R.color.my_color_0)
        )
        val gradientDrawable = GradientDrawable(Orientation.TOP_BOTTOM, colors)
        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        dataSet.fillDrawable = gradientDrawable

        // Recreate the LineData object with the updated LineDataSet
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(dataSet)
        lineData = LineData(dataSets)

        // Set the new LineData to the lineChart and redraw it
        lineChart.data = lineData
        lineChart.notifyDataSetChanged()
        lineChart.invalidate()
    }



}
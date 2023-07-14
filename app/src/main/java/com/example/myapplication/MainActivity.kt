package com.example.myapplication

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.Orientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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
    private var entries2 = ArrayList<Entry>()
    private var entries3 = ArrayList<Entry>()
    private lateinit var lineData: LineData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lineChart = findViewById(R.id.lineChart)
        btnAdd = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener {
            addItem()
        }

        lineChart.apply {
            setTouchEnabled(true)
            setPinchZoom(true)
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.isEnabled = false
            legend.isEnabled = false
            description.isEnabled = false
        }

        entries.apply {
            add(Entry(0f, 0f))
            add(Entry(1f, 4f))
            add(Entry(2f, 4f))
            add(Entry(3f, 4f))
            add(Entry(4f, 4f))
            add(Entry(5f, 14f))
        }

        entries2.apply {
            add(Entry(0f, 0f))
            add(Entry(1f, 6f))
            add(Entry(2f, 8f))
            add(Entry(3f, 10f))
            add(Entry(4f, 12f))
            add(Entry(5f, 16f))
        }

        entries3.apply {
            add(Entry(0f, 0f))
            add(Entry(1f, 5f))
            add(Entry(2f, 7f))
            add(Entry(3f, 9f))
            add(Entry(4f, 11f))
            add(Entry(5f, 15f))
        }

        val dataSet = LineDataSet(entries, "Cubic Line")
        val dataSet2 = LineDataSet(entries2, "Second Line")
        val dataSet3 = LineDataSet(entries3, "Third Line")

        val markerView = CustomMarkerView(this, R.layout.custom_marker_view, entries)
        lineChart.marker = markerView

        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.my_color),
            ContextCompat.getColor(this, R.color.my_color_0)
        )
        val gradientDrawable = GradientDrawable(Orientation.TOP_BOTTOM, colors)
        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT

        dataSet.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawFilled(false)
            setDrawCircles(false)
            lineWidth = 1.8f
            circleRadius = 30f
            valueTextColor = Color.TRANSPARENT
            highLightColor = Color.TRANSPARENT
            color = ContextCompat.getColor(this@MainActivity, R.color.my_color_04A0FE)
            fillDrawable = gradientDrawable
        }

        dataSet2.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawFilled(false)
            setDrawCircles(false)
            lineWidth = 1.8f
            circleRadius = 30f
            valueTextColor = Color.TRANSPARENT
            highLightColor = Color.TRANSPARENT
            color = ContextCompat.getColor(this@MainActivity, R.color.purple_200)
            fillDrawable = gradientDrawable
        }

        dataSet3.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawFilled(false)
            setDrawCircles(false)
            lineWidth = 1.8f
            circleRadius = 30f
            valueTextColor = Color.TRANSPARENT
            highLightColor = Color.TRANSPARENT
            color = ContextCompat.getColor(this@MainActivity, R.color.purple_500)
            fillDrawable = gradientDrawable
        }

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(dataSet)
        dataSets.add(dataSet2)
        dataSets.add(dataSet3)

        lineData = LineData(dataSets)
        lineChart.data = lineData
        lineChart.invalidate()
    }


    private fun addItem() {
        val random = Random()
        val x = entries.size.toFloat()
        val y =
            3f + (random.nextFloat() * (400000f - 3f)) // Generate random y value between 3 and 400000
        entries.add(Entry(x, y))
        entries2.add(Entry(x, y + 2))
        entries3.add(Entry(x, y - 2))

        val lastEntries = entries.takeLast(10)
        val lastEntries2 = entries2.takeLast(10)
        val lastEntries3 = entries3.takeLast(10)

        val dataSet = LineDataSet(lastEntries, "Cubic Line")
        val dataSet2 = LineDataSet(lastEntries2, "Second Line")
        val dataSet3 = LineDataSet(lastEntries3, "Third Line")

        val markerView = CustomMarkerView(this, R.layout.custom_marker_view, entries)
        lineChart.marker = markerView

        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.my_color),
            ContextCompat.getColor(this, R.color.my_color_0)
        )
        val gradientDrawable = GradientDrawable(Orientation.TOP_BOTTOM, colors)
        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT

        dataSet.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawFilled(false)
            setDrawCircles(false)
            lineWidth = 1.8f
            circleRadius = 30f
            valueTextColor = Color.TRANSPARENT
            highLightColor = Color.TRANSPARENT
            color = ContextCompat.getColor(this@MainActivity, R.color.my_color_04A0FE)
            fillDrawable = gradientDrawable
        }

        dataSet2.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawFilled(false)
            setDrawCircles(false)
            lineWidth = 1.8f
            circleRadius = 30f
            valueTextColor = Color.TRANSPARENT
            highLightColor = Color.TRANSPARENT
            color = ContextCompat.getColor(this@MainActivity, R.color.purple_200)
            fillDrawable = gradientDrawable
        }

        dataSet3.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawFilled(false)
            setDrawCircles(false)
            lineWidth = 1.8f
            circleRadius = 30f
            valueTextColor = Color.TRANSPARENT
            highLightColor = Color.TRANSPARENT
            color = ContextCompat.getColor(this@MainActivity, R.color.purple_500)
            fillDrawable = gradientDrawable
        }

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(dataSet)
        dataSets.add(dataSet2)
        dataSets.add(dataSet3)

        lineData = LineData(dataSets)
        lineChart.data = lineData
        lineChart.notifyDataSetChanged()
        lineChart.invalidate()
    }
}

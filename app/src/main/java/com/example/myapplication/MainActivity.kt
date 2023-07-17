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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lineChart = findViewById(R.id.lineChart)
        btnAdd = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener {
            addItem()
        }
        setUpLineChart()

    }


    private fun addItem() {
        val random = Random()
        val x = entries.size.toFloat()
        entries.add(Entry(x, 3f + (random.nextFloat() * (10f - 0f))))
        entries2.add(Entry(x, 3f + (random.nextFloat() * (10f - 0f))))
        entries3.add(Entry(x, 3f + (random.nextFloat() * (10f - 0f))))

        updateLineChart()
    }

    private fun setUpLineChart() {
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
        val markerView = CustomMarkerView(this, R.layout.custom_marker_view, entries)
        lineChart.marker = markerView

        entries.apply {
            add(Entry(0f, 0f))
        }

        entries2.apply {
            add(Entry(0f, 0f))
        }

        entries3.apply {
            add(Entry(0f, 0f))
        }
        updateLineChart()
    }

    private fun updateLineChart() {
        val lastEntries = entries.takeLast(20)
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

        dataSet2.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawFilled(false)
            setDrawCircles(false)
            lineWidth = 3f
            circleRadius = 30f
            valueTextColor = Color.TRANSPARENT
            highLightColor = Color.TRANSPARENT
            color = ContextCompat.getColor(this@MainActivity, R.color.purple_200)
        }

        dataSet3.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawFilled(false)
            setDrawCircles(false)
            lineWidth = 3f
            circleRadius = 30f
            valueTextColor = Color.TRANSPARENT
            highLightColor = Color.TRANSPARENT
            color = ContextCompat.getColor(this@MainActivity, R.color.purple_500)
        }

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

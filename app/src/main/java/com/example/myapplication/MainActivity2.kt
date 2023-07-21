package com.example.myapplication


import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val lineChart: LineChart = findViewById(R.id.lineChart)
        setupLineChart(lineChart)

        val entries = ArrayList<Entry>()
        // Thêm dữ liệu vào entries ở đây
        entries.apply {
            add(Entry(0f, 0f))
            add(Entry(1f, 5f))
            add(Entry(2f, 2f))
            add(Entry(3f, 1f))
            add(Entry(4f, 5f))
            add(Entry(5f, 3f))
            add(Entry(6f, 0f))
            add(Entry(7f, 5f))
            add(Entry(8f, 2f))
            add(Entry(9f, 1f))
            add(Entry(10f, 5f))
            add(Entry(11f, 3f))
        }

        updateData(lineChart, entries)

        // Thiết lập Highlight cho điểm dữ liệu đầu tiên
        lineChart.highlightValue(2f, 2, false)
    }

    private fun setupLineChart(lineChart: LineChart) {
        lineChart.setDrawGridBackground(false)
        lineChart.description.isEnabled = false
        lineChart.axisLeft.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.legend.isEnabled = false
        lineChart.animateXY(1000, 1000)
        lineChart.setTouchEnabled(true)

        lineChart.setOnChartValueSelectedListener(
            object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    // Xử lý sự kiện khi người dùng nhấn vào điểm dữ liệu
                }

                override fun onNothingSelected() {
                    // Xử lý sự kiện khi không có điểm dữ liệu nào được chọn
                }
            }
        )
        lineChart.setMaxVisibleValueCount(6)
    }

    private fun updateData(lineChart: LineChart, entries: List<Entry>) {
        val dataSet = LineDataSet(entries, "Line Data")
        dataSet.color = Color.BLUE
        dataSet.setCircleColor(Color.RED)
        dataSet.circleRadius = 5f
        dataSet.lineWidth = 2f
        dataSet.setDrawValues(true)

        val dataSets = ArrayList<LineDataSet>()
        dataSets.add(dataSet)

        val data = LineData(dataSets as List<ILineDataSet>?)
        lineChart.data = data

        lineChart.invalidate()
    }
}

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

    private lateinit var networkStatusCallback: NetworkStatusCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lineChart = findViewById(R.id.lineChart)
        btnAdd = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener {
            addItem()

        }
        setupLineChart()
        networkStatusCallback = NetworkStatusCallback(this)
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(networkStatusCallback)

    }

    override fun onDestroy() {
        super.onDestroy()

        // Hủy đăng ký callback
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(networkStatusCallback)
    }

    private fun addItem() {
        val random = Random()
        val x = entries.size.toFloat()
        entries.add(Entry(x, 3f + (random.nextFloat() * (10f - 0f))))
        entries2.add(Entry(x, 3f + (random.nextFloat() * (10f - 0f))))
        entries3.add(Entry(x, 3f + (random.nextFloat() * (10f - 0f))))
        var maxY = Float.MIN_VALUE
        var maxEntryIndex = -1

        for (i in entries.indices) {
            val currentY = entries[i].y

            if (currentY > maxY) {
                maxY = currentY
                maxEntryIndex = i
            }
        }

        val markerView = CustomMarkerView(this, R.layout.custom_marker_view, entries)
        markerView.refreshContent(entries[maxEntryIndex], Highlight(0f, maxEntryIndex, 0))

        lineChart.marker = markerView
        updateLineChart()
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

    private fun checkStatusNetWork() {
        val connectivityManager: ConnectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.allNetworks.forEach { network ->
            connectivityManager.getNetworkInfo(network)?.apply {
                if (type == ConnectivityManager.TYPE_WIFI) {
//                    isWifiConn = isWifiConn or isConnected
                    println("type network:>>> ${type}")
                }
                if (type == ConnectivityManager.TYPE_MOBILE) {
//                    isMobileConn = isMobileConn or isConnected
                    println("type network:>>> ${type}")
                }
            }
        }
    }

    private fun checkNetworkStatus2(context: Context): List<String> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkStatusList = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networks = connectivityManager.allNetworks
            for (network in networks) {
                val capabilities = connectivityManager.getNetworkCapabilities(network)

                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        networkStatusList.add("Wifi")
                    }
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        networkStatusList.add("Mạng di động")
                    }
                }
            }
        } else {
            val networkInfos = connectivityManager.allNetworkInfo

            for (networkInfo in networkInfos) {
                if (networkInfo != null && networkInfo.isConnected) {
                    if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                        networkStatusList.add("Wifi")
                    }
                    if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                        networkStatusList.add("Mạng di động")
                    }
                }
            }
        }

        if (networkStatusList.isEmpty()) {
            networkStatusList.add("Không có kết nối mạng")
        }

        return networkStatusList
    }

    private fun chkStatus() {

        val connMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)

        if (wifi!!.isConnectedOrConnecting) {
            println("connect wifi")
        }

        var mobileDataEnabled = false // Assume disabled

        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            val cmClass = Class.forName(cm.javaClass.name)
            val method = cmClass.getDeclaredMethod("getMobileDataEnabled")
            method.isAccessible = true // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = method.invoke(cm) as Boolean
            Toast.makeText(this@MainActivity, "No Network " + mobileDataEnabled, Toast.LENGTH_LONG)
                .show()
            if (mobileDataEnabled) {
                cm.activeNetwork?.let { theNetwork ->

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

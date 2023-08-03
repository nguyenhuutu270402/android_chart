package com.example.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity3 : AppCompatActivity() {

    private lateinit var networkStatusCallback: NetworkStatusCallback
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        textView = findViewById(R.id.textView)

        // Khởi tạo NetworkStatusCallback
        networkStatusCallback = NetworkStatusCallback(this) { text ->
            // Gọi hàm để đặt văn bản lên giao diện người dùng
            setText00(text)
        }

        // Khởi tạo ConnectivityManager
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Đăng ký Callback để theo dõi trạng thái mạng
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkStatusCallback)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Hủy đăng ký Callback khi hoạt động bị hủy
//        connectivityManager.unregisterNetworkCallback(networkStatusCallback)
    }

    private fun setText00(str: String){
        textView.text = str
    }
}

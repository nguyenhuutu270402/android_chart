package com.example.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

class NetworkStatusCallback(private val context: Context, private val setText00: (String) -> Unit) : ConnectivityManager.NetworkCallback() {

    val networkStatusList = mutableListOf<String>()

    override fun onAvailable(network: Network) {
        updateNetworkStatus()
    }

    override fun onLost(network: Network) {
        updateNetworkStatus()
    }

    private fun updateNetworkStatus() {
        networkStatusList.clear()

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networks = connectivityManager.allNetworks

        for (network in networks) {
            val capabilities = connectivityManager.getNetworkCapabilities(network)

            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    networkStatusList.add("Wifi")
                    println("network>>>>0: wifi")
                }
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    networkStatusList.add("Mạng di động")
                    println("network>>>>0: Mạng di động")

                }
            }
        }

        if (networkStatusList.isEmpty()) {
            networkStatusList.add("Không có kết nối mạng")
        }

        println("network>>>>>>>>>>>>>>: $networkStatusList")

    }
}

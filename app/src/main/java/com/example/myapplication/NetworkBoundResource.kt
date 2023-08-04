package com.example.myapplication

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NetworkBoundResource(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkStatus: LiveData<Boolean> = MutableLiveData()

    fun checkNetworkStatus() {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected
        (networkStatus as MutableLiveData).postValue(isConnected)
    }
}

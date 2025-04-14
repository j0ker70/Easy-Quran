package com.example.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ConnectivityManagerNetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
) : NetworkMonitor {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isCurrentlyConnected() = connectivityManager.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            it.activeNetwork
                ?.let(it::getNetworkCapabilities)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            it.activeNetworkInfo?.isConnected == true
        }
    } == true
}
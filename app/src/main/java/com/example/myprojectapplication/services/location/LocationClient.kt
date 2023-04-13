package com.example.myprojectapplication.services.location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myprojectapplication.screens.home.HomeFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationClient(private val fragment: Fragment) {
    private var fusedLocationClient: FusedLocationProviderClient? =
        fragment.context?.let { LocationServices.getFusedLocationProviderClient(it) }
    
    suspend fun getLastLocation(): Location? {
        return suspendCancellableCoroutine { cont ->
            val context = fragment.context
            if (context?.let {
                    ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED
                ||
                context.let {
                    ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("LocationClient", "request permission")
                cont.resume(null)
            } else {
                val callback =
                    OnSuccessListener<Location> { location ->
                        cont.resume(location)
                    }
                fusedLocationClient?.lastLocation?.addOnSuccessListener(callback)
            }

        }
    }


//    fun getDistanceInMeters(locationA: Location?, latB: Double?, lngB: Double?): Float? {
//        if (latB == null || lngB == null) {
//            return null
//        }
//        return locationA?.let {
//            val results = FloatArray(1)
//            Location.distanceBetween(locationA.latitude, locationA.longitude, latB, lngB, results)
//            results[0]
//        }
//    }
//
//    fun getDistanceInMiles(locationA: Location?, latB: Double?, lngB: Double?): Float? {
//        val distance = getDistanceInMeters(locationA, latB, lngB)
//        return distance?.let {
//            it / 1609.344f
//        }
//    }
}
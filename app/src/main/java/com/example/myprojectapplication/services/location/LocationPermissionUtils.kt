package com.watasolutions.week8_permission.services.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


class LocationPermissionUtils {
    private var listener: LocationComponentListener? = null
    private val permissionId = 2
    interface LocationComponentListener {
        fun onLocationFeatureEnable(isEnabled: Boolean)
    }

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    fun registerActivityResultForPermission(fragment: Fragment) {
        requestPermissionLauncher =
            fragment.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                listener?.onLocationFeatureEnable(isGranted)
            }
    }


    fun setListener(listener: LocationComponentListener?) {
        this.listener = listener
    }

//    fun isLocationFeatureIsEnabled(context: Context): Boolean {
//        return isLocationPermissionIsAllow(context)
//    }

    fun requestLocationPermission(
        fragment: Fragment,
    ) {
        fragment.requireContext()
        if (isLocationPermissionIsAllow(fragment)) {
            listener?.onLocationFeatureEnable(true)
        } else {
            fragment.activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    permissionId
                )
            }
        }
    }

    private fun isLocationPermissionIsAllow(fragment: Fragment): Boolean {
        return fragment.context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } == PackageManager.PERMISSION_GRANTED
                && fragment.context?.let {
                    ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
        } == PackageManager.PERMISSION_GRANTED

    }

    fun openLocationInAppSetting(context: Context) {
        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    fun handleLocationPermission(isLocationGranted: Boolean) {
        listener?.onLocationFeatureEnable(isLocationGranted)
    }

}
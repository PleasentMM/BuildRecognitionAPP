package com.example.buildingrecognition

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * used for
 * style by imurluck
 * style at 2019-08-10
 */

internal fun Context.hasPermissions(permissions: Array<String>) =
    permissions.all { permission ->
        ContextCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_GRANTED
    }



class PermissionFragment : Fragment() {

    lateinit var requestPermissions: Array<String>

    var permissionCallback: ((grantedPermissions: List<String>?,
                              deniedPermissions: List<String>?) -> Unit)? = null

    fun request(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction().add(this, TAG).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!requireActivity().hasPermissions(requestPermissions)) {
            requestPermissions(requestPermissions, PERMISSION_REQUEST_CODE)
        } else {
            permissionCallback?.invoke(requestPermissions.asList(), null)
            fragmentManager?.beginTransaction()?.remove(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            fragmentManager?.beginTransaction()?.remove(this)
            return
        }
        val grantedPermissions = mutableListOf<String>()
        val deniedPermissions = mutableListOf<String>()
        grantResults.forEachIndexed { index, it ->
            if (it == PackageManager.PERMISSION_GRANTED) {
                grantedPermissions.add(permissions[index])
            } else if (it == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permissions[index])
            }
        }
        permissionCallback?.invoke(grantedPermissions, deniedPermissions)
        fragmentManager?.beginTransaction()?.remove(this)
    }

    companion object {
        private const val TAG = "PermissionFragment"

        const val PERMISSION_REQUEST_CODE = 0xff

    }
}
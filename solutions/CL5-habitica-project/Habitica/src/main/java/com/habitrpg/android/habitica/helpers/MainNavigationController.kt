package com.habitrpg.android.habitica.helpers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import java.lang.ref.WeakReference
import java.util.*
import kotlin.math.abs

object MainNavigationController {
    var lastNavigation: Date? = null

    private var controllerReference: WeakReference<NavController>? = null

    private val navController: NavController?
        get() { return controllerReference?.get() }

    fun setup(navController: NavController) {
        this.controllerReference = WeakReference(navController)
    }

    fun navigate(transactionId: Int, args: Bundle? = null) {
        if (abs((lastNavigation?.time ?: 0) - Date().time) > 500) {
            lastNavigation = Date()
            try {
                navController?.navigate(transactionId, args)
            } catch (e: IllegalArgumentException) {
                Log.e("Main Navigation", e.localizedMessage ?: "")
            } catch (error: Exception) {
                Log.e("Main Navigation", error.localizedMessage ?: "")
            }
        }
    }

    fun navigate(directions: NavDirections) {
        if (abs((lastNavigation?.time ?: 0) - Date().time) > 500) {
            lastNavigation = Date()
            try {
                navController?.navigate(directions)
            } catch (_: IllegalArgumentException) {}
        }
    }

    fun navigate(uriString: String) {
        val uri = Uri.parse(uriString)
        navigate(uri)
    }

    fun navigate(uri: Uri) {
        if (navController?.graph?.hasDeepLink(uri) == true) {
            navController?.navigate(uri)
        }
    }

    fun navigate(request: NavDeepLinkRequest) {
        if (navController?.graph?.hasDeepLink(request) == true) {
            navController?.navigate(request)
        }
    }

    fun handle(deeplink: Intent) {
        navController?.handleDeepLink(deeplink)
    }
}

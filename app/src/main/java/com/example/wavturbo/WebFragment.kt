package com.example.wavturbo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.hotwire.turbo.fragments.TurboWebFragment
import dev.hotwire.turbo.nav.TurboNavGraphDestination
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

@TurboNavGraphDestination(uri = "turbo://fragment/web")
open class WebFragment : TurboWebFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.web_fragment, container, false)
    }

    override fun shouldObserveTitleChanges(): Boolean {
        return false
    }

    override fun shouldNavigateTo(newLocation: String): Boolean {
        return when (newLocation.contains("sign_in")  || newLocation.contains("sign_up") || newLocation.contains("?oauth_token=")) {
            true -> {
                true
            }
            else -> {
                val uri = Uri.parse(newLocation).buildUpon()
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                val authToken = sharedPref?.getString(getString(R.string.preference_oauth_token), null)
                uri.appendQueryParameter("oauth_token", authToken)
                val locationWithToken = uri.toString()
                this.navigate(locationWithToken)
                false
            }
        }
    }
}
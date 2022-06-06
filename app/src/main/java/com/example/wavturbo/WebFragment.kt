package com.example.wavturbo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.hotwire.turbo.fragments.TurboWebFragment
import dev.hotwire.turbo.nav.TurboNavGraphDestination
import java.net.HttpURLConnection
import java.net.URL

@TurboNavGraphDestination(uri = "turbo://fragment/web")
open class WebFragment : TurboWebFragment() {
    lateinit var currentUserId: String 
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.web_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCurrentUser()
        val bottomNavigation: BottomNavigationView = view.findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    navigate(HOME_URL)
                    true
                }
                R.id.chats -> {
                    navigate(CHATS_URL + currentUserId)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun shouldObserveTitleChanges(): Boolean {
        return false
    }
}
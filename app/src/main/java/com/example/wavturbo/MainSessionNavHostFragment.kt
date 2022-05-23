package com.example.wavturbo

import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dev.hotwire.turbo.session.TurboSessionNavHostFragment
import dev.hotwire.turbo.config.TurboPathConfiguration
import kotlin.reflect.KClass
import  com.example.wavturbo.WebFragment

class MainSessionNavHostFragment : TurboSessionNavHostFragment() {
    override val sessionName = "main"

    override val startLocation = "http://10.0.2.2:3000"

    override val registeredActivities: List<KClass<out AppCompatActivity>>
        get() = listOf(
            // Leave empty unless you have more
            // than one TurboActivity in your app
        )

    override val registeredFragments: List<KClass<out Fragment>>
        get() = listOf(
            WebFragment::class
        )

    override val pathConfigurationLocation: TurboPathConfiguration.Location
        get() = TurboPathConfiguration.Location(
            assetFilePath = "json/configuration.json"
        )

    override fun onSessionCreated() {
        super.onSessionCreated()
        session.webView.settings.userAgentString = customUserAgent(session.webView)
    }

    private fun customUserAgent(webView: WebView): String {
        return "Turbo Native Android ${webView.settings.userAgentString}"
    }
}

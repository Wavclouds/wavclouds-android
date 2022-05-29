package com.example.wavturbo

import android.os.Bundle
import dev.hotwire.turbo.fragments.TurboWebFragment
import dev.hotwire.turbo.nav.TurboNavGraphDestination

@TurboNavGraphDestination(uri = "turbo://fragment/web")
open class WebFragment : TurboWebFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupMenu()
    }

    private fun setupMenu() {
        toolbarForNavigation()?.inflateMenu(R.menu.menu)
    }

    override fun shouldObserveTitleChanges(): Boolean {
        return false
    }
}
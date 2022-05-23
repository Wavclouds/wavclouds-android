package com.example.wavturbo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.hotwire.turbo.activities.TurboActivity
import dev.hotwire.turbo.delegates.TurboActivityDelegate

class MainActivity : AppCompatActivity(), TurboActivity {
    override lateinit var delegate: TurboActivityDelegate
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        delegate = TurboActivityDelegate(this, R.id.main_nav_host)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    delegate.navigate(location= "/")
                }
            }
            false
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
package com.example.wavturbo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wavturbo.auth.AuthResult
import com.example.wavturbo.auth.User
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.hotwire.turbo.activities.TurboActivity
import dev.hotwire.turbo.delegates.TurboActivityDelegate

class MainActivity : AppCompatActivity(), TurboActivity {
    override lateinit var delegate: TurboActivityDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val authToken = sharedPref.getString(getString(R.string.preference_oauth_token), null)
        if (authToken != null && authToken != "") {
            setContentView(R.layout.main_activity)
            delegate = TurboActivityDelegate(this, R.id.main_nav_host)
            CookieManager.getInstance().setCookie(BASE_URL, "oauth_token=${authToken}");
            var userId: String = ""
            Fuel.post(USER_ID_URL, listOf("oauth_token" to authToken)).responseObject(User.Deserializer()) { req, res, result ->
                //result is of type Result<User, Exception>
                val (body, err) = result
                if (res.statusCode.toString() == "200") {
                   userId = body?.user_id.toString()
                }
            }
            var menuBar = findViewById(R.id.bottom_navigation) as BottomNavigationView
            val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
                Log.d("NAV", userId)
                when (item.itemId) {
                    R.id.home -> {
                        // put your code here
                        delegate.navigate(BASE_URL)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.chats -> {
                        // put your code here
                        delegate.navigate(BASE_URL + "/${userId}/chats")
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.upload -> {
                        delegate.navigate(BASE_URL + "/audios/new")
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.profile -> {
                        delegate.navigate(BASE_URL + "/${userId}")
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }
            menuBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        } else {
            setContentView(R.layout.login_activity)
            var et_user_name = findViewById(R.id.et_user_name) as EditText
            var et_password = findViewById(R.id.et_password) as EditText
            var btn_submit = findViewById(R.id.btn_submit) as Button
            val errorElement = findViewById(R.id.login_error) as TextView
            btn_submit.setOnClickListener {
                val login = et_user_name.text.toString();
                val password = et_password.text.toString();

                Fuel.post(OAUTH_TOKEN_URL, listOf("login" to login, "password" to password)).responseObject(AuthResult.Deserializer()) { req, res, result ->
                    //result is of type Result<User, Exception>
                    val (body, err) = result
                    if (res.statusCode.toString() == "200") {
                        with (sharedPref.edit()) {
                            putString(getString(R.string.preference_oauth_token), body?.oauth_token)
                            apply()
                        }
                        finish();
                        startActivity(getIntent());
                    } else {
                        errorElement.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
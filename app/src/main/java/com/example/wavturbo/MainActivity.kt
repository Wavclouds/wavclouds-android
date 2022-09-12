package com.example.wavturbo

import android.content.Context
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wavturbo.auth.AuthService
import com.example.wavturbo.auth.User
import com.github.kittinunf.fuel.Fuel
import dev.hotwire.turbo.activities.TurboActivity
import dev.hotwire.turbo.delegates.TurboActivityDelegate
import org.json.JSONObject

class MainActivity : AppCompatActivity(), TurboActivity {
    override lateinit var delegate: TurboActivityDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val authToken = sharedPref.getString(R.string.preference_oauth_token.toString(), null)

        if (authToken != null) {
            Log.d("TAG", "You are authenticated")
            setContentView(R.layout.main_activity)
        } else {
            setContentView(R.layout.login_activity)
            var et_user_name = findViewById(R.id.et_user_name) as EditText
            var et_password = findViewById(R.id.et_password) as EditText
            var btn_submit = findViewById(R.id.btn_submit) as Button
            val errorElement = findViewById(R.id.login_error) as TextView
            btn_submit.setOnClickListener {
                val login = et_user_name.text.toString();
                val password = et_password.text.toString();

                Fuel.post(OAUTH_TOKEN_URL, listOf("login" to login, "password" to password, "grant_type" to "password")).responseObject(User.Deserializer()) { req, res, result ->
                    //result is of type Result<User, Exception>
                    val (body, err) = result
                    if (res.statusCode.toString() == "200") {
                        with (sharedPref.edit()) {
                            putString(getString(R.string.preference_oauth_token), body?.access_token)
                            apply()
                        }
                        setContentView(R.layout.main_activity)
                    } else {
                        Log.d("LOGIN", "LOGIN FAILED")
                        errorElement.visibility = android.view.View.VISIBLE
                    }
                }
            }
        }
    }
}
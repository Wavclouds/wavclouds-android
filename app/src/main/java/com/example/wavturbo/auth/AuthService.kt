package com.example.wavturbo.auth

import android.util.Log
import com.example.wavturbo.OAUTH_TOKEN_URL
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody

class AuthService(val login: String, val password: String) {
    var success: Boolean = false
    var access_token: String? = ""
    var refresh_token: String? = ""

    fun call() {

    }
}
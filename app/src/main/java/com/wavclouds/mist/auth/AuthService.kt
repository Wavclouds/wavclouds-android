package com.wavclouds.mist.auth

class AuthService(val login: String, val password: String) {
    var success: Boolean = false
    var oauth_token: String? = ""

    fun call() {

    }
}
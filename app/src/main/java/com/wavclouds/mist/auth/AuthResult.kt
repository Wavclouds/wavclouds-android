package com.wavclouds.mist.auth

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

//User Model
data class AuthResult(val oauth_token: String = "") {

    //User Deserializer
    class Deserializer : ResponseDeserializable<AuthResult> {
        override fun deserialize(content: String) = Gson().fromJson(content, AuthResult::class.java)
    }

}
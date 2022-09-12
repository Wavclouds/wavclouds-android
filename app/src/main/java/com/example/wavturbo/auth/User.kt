package com.example.wavturbo.auth

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

//User Model
data class User(val access_token: String = "",
                val refresh_token: String = "") {

    //User Deserializer
    class Deserializer : ResponseDeserializable<User> {
        override fun deserialize(content: String) = Gson().fromJson(content, User::class.java)
    }

}
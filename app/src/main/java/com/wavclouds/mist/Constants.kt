package com.wavclouds.mist
val BASE_URL: String = if (BuildConfig.DEBUG) "https://www.wavclouds.com" else "http://10.0.2.2:3000"
val OAUTH_TOKEN_URL = BASE_URL + "/oauth/token"
val USER_ID_URL = BASE_URL + "/oauth/user_id"
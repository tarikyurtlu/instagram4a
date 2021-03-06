package com.bodiart.instagram4a.refactor.kotlin

import com.bodiart.instagram4a.refactor.kotlin.payload.other.InstagramLoginPayload
import com.bodiart.instagram4a.refactor.kotlin.utils.InstagramConstants
import com.bodiart.instagram4a.refactor.kotlin.utils.InstagramGenericUtil
import com.bodiart.instagram4a.refactor.kotlin.utils.InstagramHashUtil
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class Instagram4Android {

    private lateinit var deviceId: String
    private lateinit var accessToken : String
    private lateinit var uuid : String
    private lateinit var rankToken : String
    lateinit var client : OkHttpClient private set
    private var userId = -1
    private var username = ""
    private var password = ""
    private var isLoggedIn = false
    private val cookieStore = HashMap<String, Cookie>()

    fun setup(okClient: OkHttpClient? = null){
        deviceId = InstagramHashUtil.generateDeviceId(username, password)
        uuid = InstagramGenericUtil.generateUuid(true)

        client = okClient ?: makeOkHttpClient()
    }

    @Deprecated("Get keys with web view")
    @Throws(Exception::class)
    fun login(username : String, password : String){
        val loginRequest = InstagramLoginPayload(
                username = username,
                password = password,
                guid = uuid,
                devideId = deviceId,
                phoneId = InstagramGenericUtil.generateUuid(true),
                loginAttemptAccount = 0,
//                _csrftoken = getOrFetchCsrf(null)
                _csrftoken = ""
        )

//        val loginResult = sendRequest(InstagramLoginRequest())
    }




    // HELP

    private fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .cookieJar(object : CookieJar{

                    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                        for (cookie in cookies)
                            cookieStore[cookie.name] = cookie
                    }

                    override fun loadForRequest(url: HttpUrl): List<Cookie> {
                        val validCookies = ArrayList<Cookie>()
                        for ( (_, cookie) in cookieStore) {

                            if (cookie.expiresAt >= System.currentTimeMillis())
                                validCookies.add(cookie)
                        }
                        return validCookies
                    }
                })
                .connectTimeout(InstagramConstants.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(InstagramConstants.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build()
    }
}
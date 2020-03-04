package com.kryptkode.template.app.data.remote.api

import android.content.Context
import com.kryptkode.template.BuildConfig
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by kryptkode on 10/23/2019.
 */

class RestClient (context: Context) {

    companion object {
        private const val TIMEOUT = 50L
    }

    private val api: Api

    init {
        val loggingInterceptor = makeLoggingInterceptor(BuildConfig.DEBUG)

        val httpClient = OkHttpClient.Builder()
            .apply {
                connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                readTimeout(TIMEOUT, TimeUnit.SECONDS)
                addInterceptor(ChuckInterceptor(context))
                addInterceptor(loggingInterceptor)
            }

        val client = httpClient.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(Api::class.java)
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    fun getRemote() = api
}

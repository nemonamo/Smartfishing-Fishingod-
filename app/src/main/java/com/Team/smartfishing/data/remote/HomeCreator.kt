package com.Team.smartfishing.data.remote

import com.Team.smartfishing.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HomeCreator {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("X-M2M-Origin", BuildConfig.MOBIUS_ORIGIN)
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.MOBIUS_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val homeService: HomeService = retrofit.create(HomeService::class.java)


}

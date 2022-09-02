package com.example.travelplan_finalfianl.naver

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NaverMapServerAPI {
    companion object {
        private var retrofit: Retrofit? = null
        private val BASE_URL3 = "https://naveropenapi.apigw.ntruss.com/"

        fun getRetrofit(): Retrofit {

            val naverMapClientId = "g0k1f55l7j"
            val naverMapClientSecret = "atXycqNz2ReYs6oQwTEGWge5UQR1UUPCWX6z49Sj"

            val interceptor = Interceptor {
                with(it) {
                    val newRequest = request().newBuilder()
                        .addHeader("X-NCP-APIGW-API-KEY-ID", naverMapClientId)
                        .addHeader("X-NCP-APIGW-API-KEY", naverMapClientSecret)
                        .build()
                    proceed(newRequest)
                }
            }

            val myClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL3)
                    .client(myClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!

        }
    }
}

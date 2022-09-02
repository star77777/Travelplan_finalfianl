package com.example.travelplan_finalfianl.naver

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class NaverServerApi {


    companion object {


        private var retrofit: Retrofit? = null
        private  val baseUrl2 = "https://openapi.naver.com/v1/search/"
        fun getRetrofit(): Retrofit {
            val naverClientId="3S3FBRp0T5yGsWX5zmlV"
            val naverClientSecret="rWDGl8JtH2"


            val interceptor = Interceptor {
                with(it) {
                    val newRequest = request().newBuilder()
                        .addHeader("X-Naver-Client-Id",naverClientId)
                        .addHeader("X-Naver-Client-Secret",naverClientSecret)
                        .build()
                    proceed(newRequest)
                }
            }

            val myClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            if (retrofit == null)
            {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl2)
                    .client(myClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!

        }

    }
}
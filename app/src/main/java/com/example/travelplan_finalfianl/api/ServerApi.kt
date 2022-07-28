package com.example.travelplan_finalfianl.api

import android.content.Context
import com.example.travelplan_finalfianl.utils.ContextUtil
import com.google.gson.GsonBuilder
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ServerApi {
    companion object {

        //        서버주소
        private val baseUrl = "https://keepthetime.xyz"

        private var retrofit: Retrofit? = null
        fun getRetrofit(context: Context): Retrofit {
            if (retrofit == null) {

                //                API요청이 발생하면 => 가로채서 => 헤더를 추가해주자.
//                자동으로 헤더를 달아주는 효과 발생

                val interceptor = Interceptor {
                    with(it) {
                        val newRequest = request().newBuilder()
                            .addHeader("X-Http-Token", ContextUtil.getLoginToken(context))
                            .build()
                        proceed(newRequest)
                    }
                }


                val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter(Date::class.java, DateDeserializer())
                    .create()

                val myClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(myClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            }

            return retrofit!!
        }
    }
}

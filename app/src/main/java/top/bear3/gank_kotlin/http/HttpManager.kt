package top.bear3.gank_kotlin.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import top.bear3.gank_kotlin.BuildConfig
import java.util.concurrent.TimeUnit

/**
 * Description:
 * Author: TT
 * From: 2019/5/8
 * Last Version: 1.0.0
 * Last Change Time: 2019/5/8
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/5/8
 * *-*
 */
object HttpManager {
    private const val TIMEOUT = 10L
    private const val BASE_URL = "http://gank.io/api/"

    // Api
    var gankApi: GankApi

    // OkHttp 客户端
    private lateinit var mOkHttpClient: OkHttpClient

    init {
        gankApi = getApi(GankApi::class.java)
    }


    private fun getOkHttpClient() : OkHttpClient {
        if (!HttpManager::mOkHttpClient.isInitialized) {
            val builder = OkHttpClient.Builder()
//                builder.addInterceptor(RequestInterceptor())
                builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
//                    .dns(ApiDns())
//                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)

                if (BuildConfig.DEBUG) {
                    builder.addInterceptor(HttpLogInterceptor())
                }
                mOkHttpClient = builder.build()
        }

        return mOkHttpClient
    }

    /**
     * 获取Api
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    private fun <T> getApi(cls: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(buildGson()))//定义int，后台返回string，防止解析失败报错（解析异常默认返回0）
            .build()

        return retrofit.create(cls)
    }
}

package top.bear3.gank_kotlin.http

import android.text.TextUtils
import okhttp3.*
import okio.Buffer
import timber.log.Timber

import java.io.IOException
import java.nio.charset.Charset
import kotlin.math.min

/**
 * Description:
 * Author: TT
 * From: 2019/8/7
 * Last Version: 1.0.0
 * Last Change Time: 2019/8/7
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/8/7
 * *-*
 */
class HttpLogInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

        // 打印请求到的信息
        val sb = StringBuilder()
        sb.append("--------------------\n")
            .append("****** request ******\n")
            .append("method - ").append(request.method()).append("\n")
            // todo 打印的url中文显示不出来
            .append("url - ").append(request.url()).append("\n")
            .append("headers - ").append(request.headers())

        val requestBody = request.body()
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset: Charset? = Charset.forName("UTF-8")
            if (requestBody.contentType() != null) {
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                    if (charset == null) {
                        charset = Charset.forName("UTF-8")
                    }
                }
            }

            val body: String
            if (requestBody is FormBody) {
                body = buffer.readString(charset!!)
            } else {
                body = buffer.readString(min(REQUEST_BODY_MAX_LENGTH, buffer.size()), charset!!)
            }

            if (!TextUtils.isEmpty(body)) {
                sb.append("body - ").append(body).append("\n")
            }
        }

        sb.append("** response **\n")
        var content = ""
        if (response.body() != null) {
            content = response.body()!!.string()
            if (!TextUtils.isEmpty(content)) {
                sb.append(content).append("\n")
            }
        }
        sb.append("--------------------")

        if (response.code() == 200) {
            Timber.d(sb.toString())
        } else {
            Timber.e(sb.toString())
        }

        return response.newBuilder().body(ResponseBody.create(MediaType.parse("UTF-8"), content)).build()

    }

    companion object {
        // 打印请求body的最大值（表单除外)
        private val REQUEST_BODY_MAX_LENGTH = (128 + 64).toLong()
    }
}

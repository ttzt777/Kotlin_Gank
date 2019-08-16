package top.bear3.gank_kotlin.http

import com.google.gson.JsonParseException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import retrofit2.HttpException
import timber.log.Timber

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException


abstract class HttpObserver<T> : Observer<ResponseModel<T>> {

    protected var data: ResponseModel<T>? = null

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: ResponseModel<T>) {
        dispatchSuccess(t)
    }

    override fun onError(e: Throwable) {
        dispatchError(e)
    }

    override fun onComplete() {

    }

    /**
     * 数据解析成功且后台返回success
     *
     * @param data
     */
    protected abstract fun onSuccess(data: ResponseModel<T>)

    /**
     * 失败后调用
     */
    protected abstract fun onFailed(error: HttpError)

    // </editor-folder>

    // <editor-folder desc="开放给子类重写">

    /**
     * 数据解析成功后调用
     *
     * @param data
     */
    open protected fun onDataParsed(data: ResponseModel<T>) {
        this.data = data

        if (data.error) {
            handleError(HttpError.DATA)
            return
        }

        onSuccess(data)
    }

    /**
     * 操作完成后回调，不管成功或者失败都会调用
     */
    open protected fun onFinish() {

    }

    private fun dispatchSuccess(data: ResponseModel<T>) {
        onFinish()

        onDataParsed(data)
    }

    /**
     * 处理错误类型
     * HttpException 网络走通，返回值不是200
     * UnknownHostException 无法识别主机，一般为本地无网络
     * SocketTimeoutException 超时
     * ConnectException 连接错误
     * JsonParseException JSONException ParseException 数据解析错误
     *
     * @param e
     */
    private fun dispatchError(e: Throwable) {
        Timber.e("Http error - %s", e.toString())

        onFinish()

        var error = when (e) {
            is HttpException, is JsonParseException, is JSONException, is ParseException -> HttpError.SERVER
            is SocketTimeoutException -> HttpError.TIMEOUT
            is UnknownHostException, is ConnectException -> HttpError.NETWORK
            else -> HttpError.DEFAULT
        }

        handleError(error)
    }

    /**
     * 处理错误
     *
     * @param errorType    错误类型
     * @param errorMessage
     */
    open protected fun handleError(error: HttpError) {
        onFailed(error)

        showToast(error.message)
    }

    /**
     * Toast提示错误
     *
     * @param errorMessage
     */
    private fun showToast(errorMessage: String?) {
        errorMessage?.let {
            // todo
        }
    }
}
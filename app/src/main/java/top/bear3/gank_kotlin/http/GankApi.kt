package top.bear3.gank_kotlin.http

import io.reactivex.Observable
import retrofit2.http.GET
import top.bear3.gank_kotlin.ui.main.TodayDetail

/**
 * Description:
 * Author: TT
 * From: 2019/8/14
 * Last Version: 1.0.0
 * Last Change Time: 2019/8/14
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/8/14
 * *-*
 */
interface GankApi {
    @GET("today")
    fun getToday() : Observable<ResponseModel<Map<String, List<TodayDetail>>>>
}
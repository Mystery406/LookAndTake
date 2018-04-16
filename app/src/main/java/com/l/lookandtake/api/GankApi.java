package com.l.lookandtake.api;

import com.l.lookandtake.entity.GankData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by L on 2018/4/10.
 * Description:
 */
public interface GankApi {

    @GET("data/{type}/20/{page}")
    Observable<GankData> getGankData(@Path("type") String type, @Path("page") int page);
}
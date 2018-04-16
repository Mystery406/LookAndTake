package com.l.lookandtake.api;

import com.l.lookandtake.entity.PhotoDetail;
import com.l.lookandtake.entity.PhotoInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by L on 2018/4/10.
 * Description:
 */
public interface UnsplashApi {
    @GET("photos?client_id=5335a3129eb6e4efd9b9c92f2ccdd8e06049c35183dcf170c6aaf554c2af0f34")
    Observable<List<PhotoInfo>> getUnsplashData(@Query("page") int page, @Query("per_page") int per_page);

    @GET("photos/{photo_id}?client_id=5335a3129eb6e4efd9b9c92f2ccdd8e06049c35183dcf170c6aaf554c2af0f34")
    Observable<PhotoDetail> getPhotoDetial(@Path(value = "photo_id") String photoId);
}

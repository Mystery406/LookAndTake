package com.l.lookandtake.api;

import com.l.lookandtake.constant.ApiConstants;
import com.l.lookandtake.util.SSLSocketFactoryCompat;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by L on 2018/4/4.
 * Description:
 */
public class ApiManager {
    private volatile static ApiManager apiManager;
    private final OkHttpClient client;
    private GankApi gankApi;
    private UnsplashApi unsplashApi;

    private ApiManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
        try {
            // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
            final X509TrustManager trustAllCert =
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    };
            final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
            builder.sslSocketFactory(sslSocketFactory, trustAllCert);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        client = builder.build();
    }

    /**
     * 双重校验锁实现单例
     *
     * @return
     */
    public static ApiManager getInstance() {
        if (apiManager == null) {
            synchronized (ApiManager.class) {
                if (apiManager == null) {
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }

    public GankApi getGankService() {
        if (gankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_GANK_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            gankApi = retrofit.create(GankApi.class);
        }
        return gankApi;
    }

    public UnsplashApi getUnsplashApi() {
        if (unsplashApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_UNSPLASH_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            unsplashApi = retrofit.create(UnsplashApi.class);
        }
        return unsplashApi;
    }

}

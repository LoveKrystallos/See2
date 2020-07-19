package com.example.see2.utils;

import com.example.see2.appservice.AppService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {
    private static volatile HttpUtil Instance ;
    private final Retrofit.Builder builder;

    public static HttpUtil getInstance() {
        if (Instance == null) {
            synchronized (HttpUtil.class ){
                if (Instance == null) {
                    Instance = new HttpUtil();
                }
            }
        }
        return Instance;
    }

    private HttpUtil() {
        builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public <T> T getService(String baseUrl, Class<T> clazz){
        return builder.baseUrl(baseUrl).build().create(clazz);
    }
}

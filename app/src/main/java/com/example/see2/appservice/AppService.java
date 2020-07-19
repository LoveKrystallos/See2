package com.example.see2.appservice;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface AppService {
    String baseUrl = "https://www.seetao.com";

    //推荐
    @GET("/app/v_1_3/article/recommendlist")
//    @GET("app/v_1_3/ad/coopen")
    Call<ResponseBody>getData(@QueryMap Map<String,String> map);

    //视频
    @GET("/app/v_1_3/article/videolist")
    Call<ResponseBody>getVideoData(@QueryMap Map<String,String> map);

    //专题
    @GET("/app/v_1_3/article/speciallist")
    Call<ResponseBody>getSpecialData(@QueryMap Map<String ,String> map);

    //TabLayout
    @GET("/api/column/columnlist")
    Call<ResponseBody>getTabData(@QueryMap Map<String,String> map);
}

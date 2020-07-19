package com.example.see2.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.see2.R;
import com.example.see2.adapter.VideoAdapter;
import com.example.see2.appservice.AppService;
import com.example.see2.bean.VideoBean;
import com.example.see2.utils.HttpUtil;
import com.example.see2.utils.Parameters;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {


    private ArrayList<VideoBean.DataBean.ListBean> videolist;
    private VideoAdapter videoAdapter;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        //start 是 ⽂章开始位置，默认传0，下次请求时，⽤本接⼝返回值
        //number 是 ⽂章开始次数，默认传0，下次请求时，⽤本接⼝返回值
        //point_time 是 节点时间，默认传0，下次请求时，⽤本接⼝返回值
        //token 否 ⽤户身份标识
        HashMap<String, String> hashMap = new HashMap<>();
//        HashMap<String, String> map = Parameters.parametersMap();
        hashMap.putAll(Parameters.parametersMap());
        hashMap.put("start","0");
        hashMap.put("number","0");
        hashMap.put("point_time","0");

        HttpUtil.getInstance().getService(AppService.baseUrl, AppService.class)
                .getVideoData(hashMap)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            //信息
                            Log.d("tag",  "视频 请求参数：" + response.toString());
                            //请求的json串
                            String json = response.body().string();
                            Log.d("tag",  "视频 json数据：" + json);
                            VideoBean videoBean = new Gson().fromJson(json, VideoBean.class);
                            List<VideoBean.DataBean.ListBean> list = videoBean.getData().getList();
                            videolist.addAll(list);
                            // 刷新适配器
                            videoAdapter.notifyDataSetChanged();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("tag", t.getMessage());
                    }
                });
    }

    private void initView(View view) {
        SmartRefreshLayout srl = view.findViewById(R.id.srl);
        RecyclerView rv = view.findViewById(R.id.rv_video);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        videolist = new ArrayList<>();
        videoAdapter = new VideoAdapter(getActivity(),videolist);
        rv.setAdapter(videoAdapter);
    }

}

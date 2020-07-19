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
import com.example.see2.adapter.SpecialAdapter;
import com.example.see2.appservice.AppService;
import com.example.see2.bean.ChangBean;
import com.example.see2.bean.SpecialBean;
import com.example.see2.utils.HttpUtil;
import com.example.see2.utils.Parameters;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialFragment extends Fragment {


    private ArrayList<SpecialBean.DataBean.BannerListBean> bannerList;
    private ArrayList<SpecialBean.DataBean.ListBean> list;
    private SpecialAdapter specialAdapter;

    public SpecialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_special, container, false);

        //创建
        bannerList = new ArrayList<>();
        list = new ArrayList<>();

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
        hashMap.putAll(Parameters.parametersMap());
        hashMap.put("start","0");
        hashMap.put("number","0");
        hashMap.put("point_time","0");
        HttpUtil.getInstance().getService(AppService.baseUrl, AppService.class)
                .getSpecialData(hashMap)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            //信息
                            Log.d("tag",  "视频 请求参数：" + response.toString());
                            //请求的json串
                            String json = response.body().string();
                            Log.d("tag",  "视频 json数据：" + json);
                            //解析
                            SpecialBean specialBean = new Gson().fromJson(json, SpecialBean.class);
                            //获取数据
                            List<SpecialBean.DataBean.BannerListBean> banner_list = specialBean.getData().getBanner_list();
                            List<SpecialBean.DataBean.ListBean> lists = specialBean.getData().getList();
                            //添加数据
                            bannerList.addAll(banner_list);
                            list.addAll(lists);
                            //刷新适配器
                            specialAdapter.notifyDataSetChanged();
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
        RecyclerView rv = view.findViewById(R.id.rv_special);

        //设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //创建适配器
        specialAdapter = new SpecialAdapter(getActivity(),bannerList,list);
        //设置适配器
        rv.setAdapter(specialAdapter);
    }
}

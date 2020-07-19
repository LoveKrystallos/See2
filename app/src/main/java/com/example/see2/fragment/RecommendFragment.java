package com.example.see2.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.see2.R;
import com.example.see2.SeekActivity;
import com.example.see2.adapter.VpAdapter;
import com.example.see2.appservice.AppService;
import com.example.see2.bean.TabBean;
import com.example.see2.utils.HttpUtil;
import com.example.see2.utils.Parameters;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment {


    private ImageView iv_home1;
    private DrawerLayout dll;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private TabLayout tab;
    private ViewPager vp;
    private ImageView iv_sear;

    public RecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
//        initDemo(view);
//        initDeml();
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        iv_home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dll.openDrawer(Gravity.LEFT);
            }
        });

        iv_sear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SeekActivity.class));
            }
        });
    }

    private void initData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.putAll(Parameters.parametersMap());
        HttpUtil.getInstance().getService(AppService.baseUrl, AppService.class)
                .getTabData(hashMap)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            //信息
                            Log.d("tab",  "tab 请求参数：" + response.toString());
                            //请求的json串
                            String json = response.body().string();
                            Log.d("tab",  "tab json数据：" + json);
                            TabBean tabBean = new Gson().fromJson(json, TabBean.class);
                            List<TabBean.DataBean.ListBean> list = tabBean.getData().getList();
                            for (int i = 0; i < list.size(); i++) {
                                titles.add(list.get(i).getName());
                                fragments.add(new ChangeFragment(list.get(i).getId(),list.get(i).getType()));
                            }
                            for (TabBean.DataBean.ListBean listBean : list) {
                                titles.add(listBean.getName());
                                fragments.add(new ChangeFragment(listBean.getId(),listBean.getType()));
                            }

                            vp.setAdapter(new VpAdapter(getFragmentManager(), fragments, titles));
                            tab.setupWithViewPager(vp);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("tab",  "视频 json数据：" + t.getMessage());
                    }
                });
    }

    private void initView(View view) {
        ConstraintLayout cl_two = view.findViewById(R.id.cl_two);
        dll = view.findViewById(R.id.dll);
        iv_home1 = view.findViewById(R.id.iv_home1);
        tab = view.findViewById(R.id.tab);
        iv_sear = view.findViewById(R.id.iv_sear);
        vp = view.findViewById(R.id.vp);
        NavigationView nv = view.findViewById(R.id.nv);


        fragments = new ArrayList<>();
        titles = new ArrayList<>();
    }

//    private void initDemo(View view) {
//        try {
//            DrawerLayout dl = view.findViewById(R.id.dll);
////            LinearLayout lll = view.findViewById(R.id.lll);
////            NavigationView nvl = view.findViewById(R.id.nvl);
//            Dlayout dlayout = new Dlayout(getActivity());
//            Log.i("tag", dl.getMeasuredWidth()+"");
//            Log.i("tag", dl.getMeasuredHeight()+"");
//            dlayout.measure(dl.getMeasuredWidth(), dl.getMeasuredHeight());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void initDeml() {
        try {
            DrawerLayout drawerLayout = new DrawerLayout(getActivity());

            Class aclass = DrawerLayout.class;
            Method[] declaredMethods = aclass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                Log.i("aclass", declaredMethod + "");
            }
            Method onMeasure = aclass.getMethod("onMeasure", int.class, int.class);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


}

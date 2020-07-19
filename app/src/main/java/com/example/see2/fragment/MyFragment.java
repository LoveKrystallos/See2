package com.example.see2.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.see2.LoginActivity;
import com.example.see2.R;
import com.example.see2.appservice.AppService;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

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
public class MyFragment extends Fragment {


    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ImageView iv_mine = view.findViewById(R.id.iv_mine);
        RelativeLayout rl_jsfen = view.findViewById(R.id.rl_jsfen);
        RelativeLayout rl_start = view.findViewById(R.id.rl_start);
        RelativeLayout rl_mes = view.findViewById(R.id.rl_mes);
        RelativeLayout rl_set = view.findViewById(R.id.rl_set);
        Button btn_login = view.findViewById(R.id.btn_login);

        iv_listener(iv_mine);
        rl_listener(rl_jsfen);
        rl_listener(rl_start);
        rl_listener(rl_mes);
        btn_listener(btn_login);

    }

    public void iv_listener(ImageView iv_mine){
        iv_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    public void btn_listener(Button btn_login){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    public void rl_listener(RelativeLayout rl_jsfen){
        rl_jsfen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    public void start(){
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }


    private void initview2() {
        try {
            HashMap<String, String> pramrmap = new HashMap<>();

            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            String nonce = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            String key="K;9)Bq|ScMF1h=Vp5uA-G87d(_fi[aP,.w^{vQ:W";

            String[] arrayOfstring=new String[3];
            arrayOfstring[0]=key;
            arrayOfstring[1]=timestamp;
            arrayOfstring[2]=nonce;
            Arrays.sort(arrayOfstring);
            StringBuffer sb = new StringBuffer();
            for (String s : arrayOfstring) {
                sb.append(s);
            }
            String keystr = sb.toString();

            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(keystr.getBytes());
            byte[] bytes = digest.digest();
            StringBuffer sf = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String s = Integer.toHexString(bytes[i] & 0xff);
                if (s.length()<2){
                    sf.append(0);
                }
                sf.append(s);
            }
            String singnature = sf.toString();

            pramrmap.put("signature",singnature);
            pramrmap.put("timestamp",timestamp);
            pramrmap.put("nonce",nonce);
            pramrmap.put("from","android");
            pramrmap.put("lang","zh");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppService.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            AppService apiService = retrofit.create(AppService.class);

            /*pramrmap.put("mobile","17807193860");
            pramrmap.put("password","2013shazi");
            pramrmap.put("affirm_password","2013shazi");*/
            Call<ResponseBody> call = apiService.getData(pramrmap);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json = response.body().string();
                        Log.i("tag", json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i("tag", t.getMessage());
                }
            });
            /*postr.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Object>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Object o) {
                            Log.i("TAG", "onError: aaaaaaaaaaaaa");

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("TAG", "onError: ffffffffffffff"+e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });*/
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}

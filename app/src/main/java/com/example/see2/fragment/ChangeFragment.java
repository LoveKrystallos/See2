package com.example.see2.fragment;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.example.see2.R;
import com.example.see2.adapter.ChangeAdapter;
import com.example.see2.appservice.AppService;
import com.example.see2.bean.ChangBean;
import com.example.see2.utils.Parameters;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeFragment extends Fragment {


    private String timestamp;
    private String nonce;
    private String sha;
    private ChangeAdapter changeAdapter;
    private ArrayList<ChangBean.DataBean.ArticleListBean> articleList;
    private ArrayList<ChangBean.DataBean.BannerListBean> bannerList;
    private ArrayList<ChangBean.DataBean.FlashListBean> flashList;

    private String id;
    private int type;

    private GifImageView glf;
    private RecyclerView rv;

    public ChangeFragment() {
        // Required empty public constructor
    }

    public ChangeFragment(String id, int type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change, container, false);

        //创建存放数据的集合
        articleList = new ArrayList<>();
        bannerList = new ArrayList<>();
        flashList = new ArrayList<>();


        initView(view);
        initData();
        return view;
    }

    private void  initData() {
//        Parameters parameters = new Parameters();
//        HashMap<String, String> HashMap = parameters.parametersMap();
        HashMap<String, String> hashMap = new HashMap<>();
        HashMap<String, String> map = Parameters.parametersMap();
        hashMap.putAll(map);
        hashMap.put("id",id);
        hashMap.put("start","0");
        hashMap.put("number","0");
        hashMap.put("point_time","0");
        //用Retrofit进行网络请求
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppService.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            AppService apiService = retrofit.create(AppService.class);

            /*pramrmap.put("mobile","17807193860");
            pramrmap.put("password","2013shazi");
            pramrmap.put("affirm_password","2013shazi");*/

            Call<ResponseBody> call = apiService.getData(hashMap);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        //信息
                        Log.d("tag",  "请求参数：" + response.toString());
                        //请求的json串
                        String json = response.body().string();
                        Log.d("tag",  "json数据：" + json);
                        //解析
                        ChangBean changBean = new Gson().fromJson(json, ChangBean.class);
                        //获取数据
                        ChangBean.DataBean data = changBean.getData();
                        List<ChangBean.DataBean.ArticleListBean> article = data.getArticle_list();
                        List<ChangBean.DataBean.BannerListBean> banner = data.getBanner_list();
                        List<ChangBean.DataBean.FlashListBean> flash = data.getFlash_list();
                        articleList.addAll(article);
                        bannerList.addAll(banner);
                        flashList.addAll(flash);
                        //刷新适配器
                        changeAdapter.notifyDataSetChanged();
                        //隐藏gif
                        glf.setVisibility(View.GONE);
                        rv.setVisibility(View.VISIBLE);
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
        //获取控件
        rv = view.findViewById(R.id.rv);
        glf = view.findViewById(R.id.glf);


        //设置布局管理器 为线性管理器
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        //创建适配器
        changeAdapter = new ChangeAdapter(getActivity(), articleList, bannerList, flashList,type);
        //设置适配器
        rv.setAdapter(changeAdapter);


    }

    /**
     * 打印请求消息
     *
     * @param request 请求的对象
     */
    private String getRequestInfo(Request request) {
        String str = "";
        if (request == null) {
            return str;
        }
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            return str;
        }
        try {
            Buffer bufferedSink = new Buffer();
            requestBody.writeTo(bufferedSink);
            Charset charset = Charset.forName("utf-8");
            str = bufferedSink.readString(charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
    /**
     * 打印返回消息
     *
     * @param response 返回的对象
     */
    private String getResponseInfo(Response response) {
        String str = "";
        if (response == null || !response.isSuccessful()) {
            return str;
        }
        ResponseBody responseBody = (ResponseBody) response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("utf-8");
        if (contentLength != 0) {
            str = buffer.clone().readString(charset);
        }
        return str;
    }



    private void initView2(){
        //秘钥
        String secretkey = "K;9)Bq|ScMF1h=Vp5uA-G87d(_ﬁ[aP,.w^{vQ:W";
        //系统当前的Unix时间戳
        timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        Log.i("tag", "initView timestamp："+timestamp);
        //随机数
        nonce = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        //创建一个数组
        String[] arr = {secretkey, timestamp, nonce};
        for (String s :
                arr) {
            Log.i("tag", s);
        }
        //数组排序
        Arrays.sort(arr);
        //转换字符串
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : arr) {
            stringBuffer.append(s);
        }
        Log.i("tag", "排序后的字符串：" + stringBuffer.toString());
        //sha-1的值
//        sha = sha1(stringBuffer.toString());
        sha = shalll(stringBuffer.toString());
        Log.i("tag", "initView sha :"+sha);


        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppService.baseUrl)
                .build();
        //获取服务
        AppService apiService = retrofit.create(AppService.class);
        //创建Map键值对
        HashMap<String, String> map = new HashMap<>();
        //存值
        map.put("signature",sha);
        Log.i("tag", "initData signature："+sha);
        map.put("timestamp",timestamp);
        Log.i("tag", "initData timestamp："+timestamp);
        map.put("nonce",nonce);
        Log.i("tag", "initData nonce："+nonce);
        map.put("lang","zh");
        map.put("from","android");

        map.put("id","1");
        map.put("start","0");
        map.put("number","0");
        map.put("point_time","0");
        //获取Call对象
        Call<ResponseBody> call = apiService.getData(map);
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
    }

    private static String shalll(String keystr) {
        try {
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
            return singnature;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sha1(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            StringBuffer buf = new StringBuffer();
            byte[] bits = md.digest();
            for(int i=0;i<bits.length;i++){
                int a = bits[i];
                if(a<0) a+=256;
                if(a<16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /*private void ziThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //?signature=" + shasb + "&timestamp=" + timestamp + "&nonce=" + nonce + "&lang=zh&from=android
                    String json = "https://www.seetao.com/app/v_1_3/article/recommendlist?signature=" + sha + "&timestamp=" + timestamp + "&nonce=" + nonce + "&lang=zh&from=android";
                    URL url = new URL(json);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    if ( httpURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        String str = "";
                        StringBuffer sb =new StringBuffer();
                        while ((str = bufferedReader.readLine()) != null) {
                            sb.append(str);
                        }
                        Log.i("tag", "数据： "+sb.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/

    /*private void sha11(String sortarr) {
        //因为getInstance(),getBytes()可能出现异常，所以使用try-catch捕获
        try {
            //实例化MessageDigest对象
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            //处理数据
            md.update(sortarr.getBytes("UTF-8"));
            //哈希计算,只被调用1次
            byte[] digest = md.digest();
            //创建StringBuffer
            StringBuffer sb = new StringBuffer();
            //遍历
            for (byte b :
                    digest) {
                //0xFF 是计算机十六进制的表示： 0x就是代表十六进制，A B C D E F  分别代表10 11 12 13 14 15   F就是15  一个F 代表4位二进制：可以看做 是   8  4  2  1。
                int i = b & 0xff;
//                Log.i("tag", "i ：" + i + "");
                if (i < 2) {
                    sb.append(0);
                }
                //用Stringbuffer进行拼接,以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式。
                sb.append(Integer.toHexString(i));
            }
            //打印SHA-1   toUpperCase()方法:String 中的所有字符都转换为大写。
            shasb = sb.toString().toUpperCase();
            Log.i("tag", "SHA-1：" + shasb);

        } catch (Exception e) {
            //打印捕获到的异常
            Log.i("tag", "捕获异常 :" + e.getMessage());
        }
    }*/

}

package com.example.see2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.see2.fragment.ChangeFragment;
import com.example.see2.fragment.MyFragment;
import com.example.see2.fragment.RecommendFragment;
import com.example.see2.fragment.SpecialFragment;
import com.example.see2.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity {

    private TextView tv_toolbar;
    private Toolbar toolbar;
    private FrameLayout fl;
    private RadioButton btn_change;
    private RadioButton btn_video;
    private RadioButton btn_special;
    private RadioButton btn_my;
    private RadioGroup rg;
    private ConstraintLayout cl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }
    private void initListener() {
        //监听单选框
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_change:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new RecommendFragment()).commit();
//                        btn_change.setTextColor(Color.DKGRAY);
                        break;
                    case R.id.btn_video:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new VideoFragment()).commit();
//                        btn_video.setTextColor(Color.DKGRAY);
                        break;
                    case R.id.btn_special:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new SpecialFragment()).commit();
//                        btn_special.setTextColor(Color.DKGRAY);
                        break;
                    case R.id.btn_my:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new MyFragment()).commit();
//                        btn_my.setTextColor(Color.DKGRAY);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initView(){
        //控件
//        tv_toolbar = (TextView) findViewById(R.id.tv_toolbar);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fl = (FrameLayout) findViewById(R.id.fl);
        btn_change = (RadioButton) findViewById(R.id.btn_change);
        btn_video = (RadioButton) findViewById(R.id.btn_video);
        btn_special = (RadioButton) findViewById(R.id.btn_special);
        btn_my = (RadioButton) findViewById(R.id.btn_my);
        rg = (RadioGroup) findViewById(R.id.rg);
        cl = (ConstraintLayout) findViewById(R.id.cl);

        //默认的Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new RecommendFragment()).commit();
        btn_change.setTextColor(Color.DKGRAY);
    }
}

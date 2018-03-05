package com.zys.baiduphoneguard.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zys.baiduphoneguard.common.Constants;
import com.zys.baiduphoneguard.utils.BaiduUtils;

//判断的Activity，它用来判断什么？
//怎么判断是否第一次进入APP？通过谁去判断？ SharedPreference 共享首选项（保存一些数据量少，简单；保存应用设置信息） 数据库
//怎么使用共享首选项？

public class JudgeActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = BaiduUtils.getSharedPreferences();

        boolean flag = mSharedPreferences.getBoolean(Constants.IS_FIRST_LAUNCH,true);

        if (flag){
            //第一次启动 进入引导界面
            startActivity(new Intent(this,SplashActivity.class));
        }else {
            //否则进入欢迎界面
            startActivity(new Intent(this,WelcomeActivity.class));
        }

        finish();
    }

}

package com.zys.baiduphoneguard.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/11/13.
 */

 /*
    1.为什么要这样写
    2.这样写有什么好处
        继承的一个特点：父类有的子类全有，减少重复代码的书写
    3.这里面写什么东西*/
    /*抽象类有什么特点：
        抽象类中可以包括抽象方法与具体实现的方法，如果有抽象方法，则子类必须复写（Override）*/

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());//在父类中，如何设置布局，在此的布局通过子类指定
        ButterKnife.bind(this);
        //打印日志信息
        KLog.v(getClass().getName());
        initViews();
        initData();


        /******************配置OkhttpClient*******************/
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);


    }


    //布局文件ID
    protected abstract int getContentView();
    protected abstract void initViews(); //初始化其他控件
    protected abstract void initData(); //初始化一些数据


}


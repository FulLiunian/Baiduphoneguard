package com.zys.baiduphoneguard.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageView;

import com.zys.baiduphoneguard.BDApplication;
import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.base.BaseActivity;
import com.zys.baiduphoneguard.common.Constants;
import com.zys.baiduphoneguard.entity.UpdateInfo;
import com.zys.baiduphoneguard.services.UpdateService;
import com.zys.baiduphoneguard.utils.BaiduUtils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zys.baiduphoneguard.common.Constants.url;

public class CheckUpdateActivity extends BaseActivity {

    @BindView(R.id.iv_bigger1)
    ImageView ivBigger1;
    @BindView(R.id.iv_bigger2)
    ImageView ivBigger2;
    //红色      黄色
    private AnimatorSet animatorOne, animatorTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_check_update;
    }

    @Override
    protected void initViews() {
        //加载第一个动画
        animatorOne = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.check_update_anim);
        animatorOne.setTarget(ivBigger1);
        animatorOne.start();
        //加载第二个动画
        animatorTwo = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.check_update_anim);
        animatorTwo.setTarget(ivBigger2);
        animatorOne.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorTwo.start();
            }
        });
        animatorTwo.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorOne.start();
            }
        });
    }

    @Override
    protected void initData() {
        boolean haNet = checkNet();
        if (haNet) {
            //告诉updateActivity，有网：网速慢  访问服务器   无法访问服务器
            //搭建服务器，访问服务器得到数据  versionName versionCode  apkUrl  description
            //客户端与服务进行交互有哪几种数据交换格式：JSON/XML  使用GsonFormat实现json数据的封闭
            //使用Retrofit访问服务器

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UpdateService updateService = retrofit.create(UpdateService.class);

            Call<UpdateInfo> info = updateService.getUpdateInfo();

            info.enqueue(new Callback<UpdateInfo>() {
                @Override
                public void onResponse(Call<UpdateInfo> call, Response<UpdateInfo> response ) {
                    //判断网络是否为404
                    if (response.isSuccessful()){
                        UpdateInfo info = response.body();
                        int serverCode = response.body().getVersionCode();
                        int clientCode = BaiduUtils.getVersionCode(BDApplication.getContext());

                        if (serverCode > clientCode) {
                            Intent data = new Intent();
                            //如何将对象存储意图当中：Serializable Parcelable Bundle
                            data.putExtra("updateInfo", info);
                            setResult(Constants.NEED_UPDATE, data);
                            finish();
                        } else {
                            setResult(Constants.NOT_NEED_UPDATE);
                            finish();
                        }
                    }else{
                        setResult(Constants.NOT_FOUND);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<UpdateInfo> call, Throwable t) {
                    setResult(Constants.INTERNET_FAILED);
                    finish();
                }

            });
        } else {
            //告诉UpdateActivity，没有网啦，有返回值的Activity
            setResult(Constants.INTERNET_INTERRUPTED);
            finish();
        }
    }


    //检查网络： 无网 网速慢 服务器 数据
    public boolean checkNet() {
        //检查是否有网
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

}
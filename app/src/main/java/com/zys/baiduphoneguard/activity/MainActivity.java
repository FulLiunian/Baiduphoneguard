package com.zys.baiduphoneguard.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.RatingBar;
import com.zys.baiduphoneguard.RatingView;
import com.zys.baiduphoneguard.activity.base.BaseActivity;
import com.zys.baiduphoneguard.common.Constants;
import com.zys.baiduphoneguard.receivers.CheckUpdateBroadcast;
import com.zys.baiduphoneguard.services.LocationService;
import com.zys.baiduphoneguard.utils.DanceWageTimer;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rating_view)
    RatingView ratingView;


    int securityScore = new Random().nextInt(10) + 1; //生成一个1-10之间的随机数
    int fluencyScore = new Random().nextInt(10) + 1;
    int clearScore = new Random().nextInt(10) + 1;
    int totalScore = 0;
    @BindView(R.id.tv_Score)
    TextView tvScore;

    CheckUpdateBroadcast checkUpdateBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册广播 一定要记得取消注册，否则会报错
        checkUpdateBroadcast = new CheckUpdateBroadcast(this);
        IntentFilter filter = new IntentFilter(Constants.CHECKUPATE_BROADCST);
        this.registerReceiver(checkUpdateBroadcast, filter);

        //发送广播
        Intent intent = new Intent();
        intent.setAction(Constants.CHECKUPATE_BROADCST);
        sendBroadcast(intent);

        //启动服务
        Intent service = new Intent(this, LocationService.class);
        startService(service);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        //score >= 8 高 5=< score >= 7 中 score <= 4 低
        // 三元运算符 表达式 ？ 条件成立 执行 ： 条件不成立执行

        ratingView.addRatingBar(new RatingBar(securityScore, securityScore >= 8 ? "安全度高" : (securityScore >= 5 && securityScore <= 7 ? "安全度中" : "安全度低")));//50%
        ratingView.addRatingBar(new RatingBar(fluencyScore, fluencyScore >= 8 ? "流畅度高" : (fluencyScore >= 5 && fluencyScore <= 7 ? "流畅度中" : "流畅度低")));//30%
        ratingView.addRatingBar(new RatingBar(clearScore, clearScore >= 8 ? "清洁度高" : (clearScore >= 5 && clearScore <= 7 ? "清洁度中" : "清洁度低")));//20%
        ratingView.show();

        ratingView.setAnimatorListener(new RatingView.AnimatorListener() {
            @Override
            public void onRotateStart() {

            }

            @Override
            public void onRotateEnd() {
                totalScore = (int) ((securityScore * 0.5 + fluencyScore * 0.3 + clearScore * 0.2) * 10);
                int totalTime = DanceWageTimer.getTotalExecuteTime(totalScore, 50);
                DanceWageTimer wageTimer = new DanceWageTimer(totalTime, 50, tvScore, totalScore);
                wageTimer.start();
            }

            @Override
            public void onRatingStart() {

            }

            @Override
            public void onRatingEnd() {

            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册
        if (checkUpdateBroadcast != null) {
            unregisterReceiver(checkUpdateBroadcast);
        }
    }



    @OnClick({R.id.shoujijiasu, R.id.lajiqingli, R.id.anquanfanghu, R.id.liulianghuafei, R.id.baiBaoXiang, R.id.yingyongguanli,R.id.iv_super_mode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //手机加速
            case R.id.shoujijiasu:
                startActivity(new Intent(this, ShouJiJiaSuActivity.class));
                overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
                break;
            //垃圾清理
            case R.id.lajiqingli:
                startActivity(new Intent(this, LaJiQingLiActivity.class));
                overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
                break;
            //安全防护
            case R.id.anquanfanghu:
                startActivity(new Intent(this, AnQuanFangHuActivity.class));
                overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
                break;
            //流量话费
            case R.id.liulianghuafei:
                startActivity(new Intent(this, LiuLiangHuaFeiActivity.class));
                overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
                break;
            //百宝箱
            case R.id.baiBaoXiang:
                startActivity(new Intent(this, BaiBaoXiangActivity.class));
                overridePendingTransition(R.anim.bbx_enter_anim, R.anim.main_exit_amin);
                break;
            //应用管理
            case R.id.yingyongguanli:
                startActivity(new Intent(this, YingYongGuanLiActivity.class));
                overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
                break;
            //用户界面
            case R.id.iv_super_mode:
                startActivity(new Intent(this, UserCenterActivity.class));
                overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
                break;
        }
    }
}

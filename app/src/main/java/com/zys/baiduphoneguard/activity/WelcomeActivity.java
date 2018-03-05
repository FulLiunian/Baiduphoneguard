package com.zys.baiduphoneguard.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.base.BaseActivity;

import butterknife.BindView;

//给欢迎界面一个渐变：alpha==>1.0==>0.0 2000
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.activity_welcome)
    ConstraintLayout activityWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        //给布局添加渐变动画：属性动画
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(activityWelcome,"alpha",1.0f,0.0f);
        alphaAnimator.setDuration(2000);
        alphaAnimator.start();

        //动画结束后 进行主页面跳转
        alphaAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
}

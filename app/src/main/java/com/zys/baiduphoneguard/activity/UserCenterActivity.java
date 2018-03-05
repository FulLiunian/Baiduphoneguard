package com.zys.baiduphoneguard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class UserCenterActivity extends BaseActivity {

    @BindView(R.id.tlCenter)
    Toolbar tlCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_center;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(tlCenter);
        //设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用
        tlCenter.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
            }
        });
    }

    @Override
    protected void initData() {

    }
    @OnClick(R.id.ll_update)
    public void onViewClicked(View view) {
        startActivity(new Intent(UserCenterActivity.this,UpdateActivity.class));
        overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);

    }
}

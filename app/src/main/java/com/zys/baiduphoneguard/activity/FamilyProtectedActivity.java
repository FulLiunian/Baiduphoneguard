package com.zys.baiduphoneguard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class FamilyProtectedActivity extends BaseActivity {

    @BindView(R.id.tl_Family)
    Toolbar tlFamily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_family_protected;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(tlFamily);
        //设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用
        tlFamily.setNavigationOnClickListener(new View.OnClickListener() {
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
    @OnClick(R.id.rl_help)
    public void onViewClicked(View view) {
        startActivity(new Intent(this, OnekeyForHelpActivity.class));
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);

    }
}

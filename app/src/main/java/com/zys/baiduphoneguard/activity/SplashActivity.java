package com.zys.baiduphoneguard.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.base.BaseActivity;
import com.zys.baiduphoneguard.adapter.SplashAdapter;
import com.zys.baiduphoneguard.common.Constants;
import com.zys.baiduphoneguard.utils.BaiduUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.vp_splash)
    ViewPager vpSplash;
    @BindView(R.id.activity_splash)
    RelativeLayout activitySplash;
    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.rg_dots)
    RadioGroup rgDots;

    private List<View> pagers;

    private SplashAdapter splashAdapter;

    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        pagers = new ArrayList<>();
        //layout  => view ==> list<view>
        View firstView = View.inflate(this, R.layout.splash_first_layout, null);
        View secondView = View.inflate(this, R.layout.splash_second_layout, null);
        View thirdView = View.inflate(this, R.layout.splash_third_layout, null);

        pagers.add(firstView);
        pagers.add(secondView);
        pagers.add(thirdView);

        Button btn_go = (Button) thirdView.findViewById(R.id.btn_go);
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSharedPreferences = BaiduUtils.getSharedPreferences();
                //修改共享首选中的值
                mSharedPreferences.edit().putBoolean(Constants.IS_FIRST_LAUNCH,false).commit();
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        });

        tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到第三个页面
                vpSplash.setCurrentItem(2);
            }
        });
    }

    @Override
    protected void initData() {
        splashAdapter = new SplashAdapter(pagers);

        vpSplash.setAdapter(splashAdapter);

        vpSplash.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rgDots.check(R.id.rb_first_dot);
                        tvGo.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        rgDots.check(R.id.rb_second_dot);
                        tvGo.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        rgDots.check(R.id.rb_third_dot);
                        tvGo.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}

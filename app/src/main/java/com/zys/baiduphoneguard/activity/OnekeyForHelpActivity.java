package com.zys.baiduphoneguard.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.base.BaseActivity;
import com.zys.baiduphoneguard.fragments.FirstHelpFragment;
import com.zys.baiduphoneguard.fragments.OpenSuccessFragment;
import com.zys.baiduphoneguard.fragments.SecondHelpFragment;
import com.zys.baiduphoneguard.utils.BaiduUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OnekeyForHelpActivity extends BaseActivity {
    @BindView(R.id.tl_onekey)
    Toolbar tlOnekey;
    @BindView(R.id.iv_question)
    ImageView ivQuestion;
    private FirstHelpFragment firstHelpFragment;

    private SecondHelpFragment secondHelpFragment;

    private OpenSuccessFragment openSuccessFragment;

    //用户选中的联系人   选中的位置
    private static List<Integer> checkedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_onekey_for_help;
    }

    @Override
    protected void initViews() {
        checkedItems = new ArrayList<>();
        setSupportActionBar(tlOnekey);
        //设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用
        tlOnekey.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        SharedPreferences sp = BaiduUtils.getSharedPreferences();
        if (sp.getBoolean("hasSetUrgent",false)){
            replaceFragment();
        }else {
            initFragment();
        }
    }

    /**
     * 第一次加载的时候，初始化第一个fragment
     */
    private void initFragment() {
        if (firstHelpFragment == null) {
            firstHelpFragment = new FirstHelpFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, firstHelpFragment).addToBackStack("help").commit();
    }

    /**
     * 切换fragment
     */
    public void switchFragment() {
        if (secondHelpFragment == null) {
            secondHelpFragment = new SecondHelpFragment();
        }
        ivQuestion.setVisibility(View.GONE);
        //切换
        getSupportFragmentManager().beginTransaction().hide(firstHelpFragment).add(R.id.fl_content, secondHelpFragment).addToBackStack("help").commit();
    }

    /*
    * 替换
    * */
    public void replaceFragment(){
        if(openSuccessFragment == null){
            openSuccessFragment = new OpenSuccessFragment();
        }
        getSupportFragmentManager().popBackStack("help", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,openSuccessFragment).addToBackStack("help").commit();
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            ivQuestion.setVisibility(View.VISIBLE);
        } else {
            finish();
        }
    }

    @Override
    protected void initData() {

    }

    public static List<Integer> getCheckedItems() {
        return checkedItems;
    }

    public static void setCheckedItems(List<Integer> checkedItems) {
        OnekeyForHelpActivity.checkedItems = checkedItems;
    }
}

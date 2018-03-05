package com.zys.baiduphoneguard.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.zys.baiduphoneguard.BDApplication;
import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.base.BaseActivity;
import com.zys.baiduphoneguard.entity.ContactInfo;
import com.zys.baiduphoneguard.fragments.SearchFragment;
import com.zys.baiduphoneguard.fragments.ShowAllContactFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddContactsActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.add_toolbar)
    Toolbar addToolbar;
    private ShowAllContactFragment showAllContactFragment;

    private SearchFragment searchFragment;

    //原始的集合
    private List<ContactInfo> originContactList;
    //搜索的结果
    private List<ContactInfo> searchResult;

    private boolean isFirstSearch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_contacts;
    }

    @Override
    protected void initViews() {

        setSupportActionBar(addToolbar);
        //设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用
        addToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
            }
        });
        originContactList = BDApplication.getContactInfos();
        searchResult = new ArrayList<>();

        initFragment();

        //当输入框中输入内容的时候，显示searchfragment
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //说明：fragment的初始化需要一定的时间
                if(isFirstSearch) {
                    switchFragment();

                    isFirstSearch = false;
                }
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                //当用户输入内容时，这里就会有变化，只要一变化，马上通知searchfragment去更新界面
                searchResult.clear();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if(s.length() > 0){
                            //将你输入的东西从原来的集合中进行搜索
                            for(int i = 0;i < originContactList.size();i++){
                                ContactInfo contactInfo = originContactList.get(i);

                                if(contactInfo.getName().contains(s) || contactInfo.getPhone().contains(s)){
                                    searchResult.add(contactInfo);
                                }
                            }
                        }

                        //控件只能在主线程进行更新
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                searchFragment.updateFragment(searchResult);

                            }
                        });

                    }
                }).start();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    if(getFragmentManager().getBackStackEntryCount() > 1){
                        getFragmentManager().popBackStack();
                    }
                    isFirstSearch = true;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    //第一次进来的时候，加载此fragment
    private void initFragment() {
        if (showAllContactFragment == null) {
            showAllContactFragment = new ShowAllContactFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_contacts, showAllContactFragment).addToBackStack("contact").commit();
    }
    private void switchFragment(){
        if(searchFragment == null){
            searchFragment = new SearchFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack("contact");

        if(searchFragment.isAdded()){
            transaction.hide(showAllContactFragment).show(searchFragment).commit();
        }else{
            transaction.hide(showAllContactFragment).add(R.id.fl_contacts, searchFragment).commit();
        }

    }
    //用户点击确定
    @OnClick(R.id.tv_sure)
    public void clickSure(View view){
        finish();
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

}

package com.zys.baiduphoneguard.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class SplashAdapter extends PagerAdapter {

    //使用构造器将原始数据传递过来
    private List<View> pagers;

    public SplashAdapter(List<View> pagers) {
        this.pagers = pagers;
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    //初始化后一个页面

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = pagers.get(position);
        container.addView(view);
        return view;
    }

    //销毁前一个页面

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = pagers.get(position);
        container.removeView(view);
    }
}

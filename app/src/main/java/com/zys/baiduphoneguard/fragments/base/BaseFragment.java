package com.zys.baiduphoneguard.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/13.
 */

public abstract class BaseFragment extends Fragment {
    private View fragmentView;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null){
            fragmentView = inflater.inflate(getContentView(), container, false);

            unbinder = ButterKnife.bind(this, fragmentView);
            initViewsAndData(fragmentView);
        }

        return fragmentView;
    }

    protected abstract int getContentView();

    protected abstract void initViewsAndData(View view);

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}

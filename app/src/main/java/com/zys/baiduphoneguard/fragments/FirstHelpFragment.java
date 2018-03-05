package com.zys.baiduphoneguard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.OnekeyForHelpActivity;
import com.zys.baiduphoneguard.fragments.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FirstHelpFragment extends BaseFragment {

    Unbinder unbinder;

    @Override
    protected int getContentView() {
        return R.layout.fragment_first_help;
    }

    @OnClick(R.id.btn_openHelp)
    public void onViewClicked() {
        //因为fragment由activity统一管理，因此替换工作应该由activity做
        if(getActivity() instanceof OnekeyForHelpActivity){
            OnekeyForHelpActivity activity = (OnekeyForHelpActivity) getActivity();

            activity.switchFragment();
        }
    }

    @Override
    protected void initViewsAndData(View view) {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
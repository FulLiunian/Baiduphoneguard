package com.zys.baiduphoneguard.fragments.base;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/13.
 */

public abstract class BaseDialogFragment extends DialogFragment {

    private View fragmentView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null) {
            fragmentView = inflater.inflate(getContentView(),container,false);

            unbinder = ButterKnife.bind(this,fragmentView);
            initViewsAndData(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    //布局中Fragment的ID
    protected abstract int getContentView();
    protected abstract void initViewsAndData(View view);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder  != null){
            unbinder.unbind();
        }
    }
}

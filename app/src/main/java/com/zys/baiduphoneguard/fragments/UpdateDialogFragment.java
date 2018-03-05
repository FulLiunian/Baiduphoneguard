package com.zys.baiduphoneguard.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.entity.UpdateInfo;
import com.zys.baiduphoneguard.fragments.base.BaseDialogFragment;
import com.zys.baiduphoneguard.services.DownloadService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class UpdateDialogFragment extends BaseDialogFragment {

    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    Unbinder unbinder;

    private UpdateInfo info;

    public static UpdateDialogFragment newInstance(String msg) {
        UpdateDialogFragment updateDialogFragment = new UpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("msg", msg);
        updateDialogFragment.setArguments(args);
        return updateDialogFragment;
    }

    public static UpdateDialogFragment newInstance(UpdateInfo updateInfo) {
        UpdateDialogFragment updateDialogFragment = new UpdateDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable("info", updateInfo);

        updateDialogFragment.setArguments(args);

        return updateDialogFragment;
    }

    private String getMsg() {
        Bundle args = getArguments();
        String msg = args.getString("msg");

        return msg;
    }

    @Override
    protected int getContentView() {
        return R.layout.update_dialog_layout;
    }

    @Override
    protected void initViewsAndData(View view) {

        Bundle args = getArguments();
        info = (UpdateInfo) args.getSerializable("info");
        if (info != null) {
            btnCancel.setVisibility(View.VISIBLE);
            tvInfo.setText(info.getDescription());
        } else {
            btnCancel.setVisibility(View.GONE);
            tvInfo.setText(getMsg());
        }
    }


    @OnClick(R.id.btn_sure)
    public void clickSure(View view) {
        if (info != null){
            Intent service = new Intent(getActivity(), DownloadService.class);
            service.putExtra("downloadUrl",info.getApkUrl());
            getActivity().startService(service);
        }
        dismiss();
    }

    @OnClick(R.id.btn_cancel)
    public void clickCancel(View view) {
        dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

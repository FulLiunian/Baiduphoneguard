package com.zys.baiduphoneguard.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zys.baiduphoneguard.R;
import com.zys.baiduphoneguard.activity.base.BaseActivity;
import com.zys.baiduphoneguard.entity.UpdateInfo;
import com.zys.baiduphoneguard.fragments.UpdateDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zys.baiduphoneguard.common.Constants.INTERNET_FAILED;
import static com.zys.baiduphoneguard.common.Constants.INTERNET_INTERRUPTED;
import static com.zys.baiduphoneguard.common.Constants.NEED_UPDATE;
import static com.zys.baiduphoneguard.common.Constants.NOT_FOUND;
import static com.zys.baiduphoneguard.common.Constants.NOT_NEED_UPDATE;
import static com.zys.baiduphoneguard.common.Constants.REQUEST_CODE;

public class UpdateActivity extends BaseActivity {


    @BindView(R.id.tlb_update)
    Toolbar tlbUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_update;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(tlbUpdate);
        //设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用
        tlbUpdate.setNavigationOnClickListener(new View.OnClickListener() {
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

    /********************************版本更新***************************************************/
    @OnClick(R.id.ll_update)
    public void update(View view) {

        //启动有返回值的Activity
        startActivityForResult(new Intent(this, CheckUpdateActivity.class), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case INTERNET_INTERRUPTED:
                showDialog("没网啦。。。。。");
                break;
            case INTERNET_FAILED:
                showDialog("服务器走丢了。。。。。");
                break;
            case NEED_UPDATE:
                UpdateInfo info = (UpdateInfo) data.getSerializableExtra("updateInfo");
                showDialog(info);
                break;
            case NOT_NEED_UPDATE:
                showDialog("您使用的是最新版本，无需更新。。。。。");
                break;
            case NOT_FOUND:
                showDialog("服务器404啦！");
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showDialog(String msg) {
        FragmentManager fragmentManager = getFragmentManager();

        UpdateDialogFragment updateDialogFragment = UpdateDialogFragment.newInstance(msg);

        updateDialogFragment.show(fragmentManager, "dialog");
    }
    private void showDialog(UpdateInfo updateInfo){
        FragmentManager fragmentManager = getFragmentManager();

        UpdateDialogFragment updateDialogFragment = UpdateDialogFragment.newInstance(updateInfo);
        updateDialogFragment.show(fragmentManager, "dialog");
    }

}

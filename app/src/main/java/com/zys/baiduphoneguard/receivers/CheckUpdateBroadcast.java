package com.zys.baiduphoneguard.receivers;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.zys.baiduphoneguard.BDApplication;
import com.zys.baiduphoneguard.entity.UpdateInfo;
import com.zys.baiduphoneguard.fragments.UpdateDialogFragment;
import com.zys.baiduphoneguard.services.UpdateService;
import com.zys.baiduphoneguard.utils.BaiduUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zys.baiduphoneguard.common.Constants.url;

/**
 * Created by Administrator on 2017/11/13.
 */

public class CheckUpdateBroadcast extends BroadcastReceiver {

    private Activity act;

    @Override
    public void onReceive(Context context, Intent intent) {
        //1、判断检查日期（保存到共享首选项当中），如果检查日期为空，表示第一次进行安装使用，设置当前日期为检查日期
        //                 如果检查日期不为空，则表示已经检查过更新
        //2、如果不为空，判断当前日期- 检查日期 >= 设置的检查间隔  如果大于  检查  如果小于  不检查
        //3、检查更新
        SharedPreferences sharedPreferences = BaiduUtils.getSharedPreferences();
        String checkDate = sharedPreferences.getString("CheckDate",null);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (checkDate == null || "".equals(checkDate)){
            Date date = new Date(); // 当前日期   date ==> string --> 保存到共享首选项当中
            String strDate = simpleDateFormat.format(date);

            sharedPreferences.edit().putString("CheDate",strDate).commit();
        }else {
            //不为空
            try {
                Date nowDate = new Date();
                Date ckDate =simpleDateFormat.parse(checkDate);
                int time = getTime(nowDate,ckDate);

                if (time >= 7){
                    //检查更新
                    checkUpdate();

                    //保存当前检查的时间
                    sharedPreferences.edit().putString("CheckDate",simpleDateFormat.format(nowDate)).commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private int getTime(Date nowDate,Date checkDate){
        // 年*365 + 月 * 31  +  日 -  年*365 + 月 * 31  +  日
        //在减之前，先得到年，如果是同一年，不是同一年
        return getDayInYear(nowDate) - getDayInYear(checkDate);
    }
    private int getDayInYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int day = calendar.get(Calendar.DAY_OF_YEAR);

        return day;
    }

    private void checkUpdate(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UpdateService updateService = retrofit.create(UpdateService.class);

        Call<UpdateInfo> info = updateService.getUpdateInfo();

        info.enqueue(new Callback<UpdateInfo>() {
            @Override
            public void onResponse(Call<UpdateInfo> call, Response<UpdateInfo> response) {
                //判断网络是否为404
                if (response.isSuccessful()) {
                    
                    UpdateInfo info = response.body();
                    int serverCode = info.getVersionCode();
                    int clientCode = BaiduUtils.getVersionCode(BDApplication.getContext());

                    if (serverCode > clientCode) {
                        System.out.println(info.getApkUrl());

                        showDialog(info);

                        //System.out.println("检查到更新啦，赶紧弹出对话框提示用户下载吧！");
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateInfo> call, Throwable t) {

            }
        });
    }

    public CheckUpdateBroadcast(Activity act){
        this.act = act;
    }

    private void showDialog(UpdateInfo updateInfo){
        FragmentManager fragmentManager = act.getFragmentManager();

        UpdateDialogFragment updateDialogFragment = UpdateDialogFragment.newInstance(updateInfo);
        updateDialogFragment.show(fragmentManager, "dialog");
    }

}

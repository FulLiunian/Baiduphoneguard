package com.zys.baiduphoneguard;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.socks.library.KLog;
import com.zys.baiduphoneguard.common.Constants;
import com.zys.baiduphoneguard.dao.ContactInfoDao;
import com.zys.baiduphoneguard.dao.DaoMaster;
import com.zys.baiduphoneguard.dao.DaoSession;
import com.zys.baiduphoneguard.dao.FunctionTableDao;
import com.zys.baiduphoneguard.dao.MobileDao;
import com.zys.baiduphoneguard.entity.ContactInfo;
import com.zys.baiduphoneguard.entity.FunctionTable;
import com.zys.baiduphoneguard.utils.BaiduUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class BDApplication extends Application {



    private static Context mContext;

    private static FunctionTableDao mFunctionTableDao;
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private SQLiteDatabase db;

    private static MobileDao mobileDao;

    private static List<ContactInfo> contactInfos;

    private static ContactInfoDao contactInfoDao;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        initDatabase();

        KLog.init(BuildConfig.LOG_DEBUG, Constants.APP_DEGUG_TAG);

        initMobileDatabase();

        new Thread(new Runnable() {
            @Override
            public void run() {
                initFunctions();

                try {
                    BaiduUtils.copyDatabase(mContext, "mobile.db");

                    contactInfos = BaiduUtils.querySystemContact();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    public static Context getContext(){
        return mContext;
    }


    private void initDatabase(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"func_db");

        db = helper.getWritableDatabase();

        daoMaster = new DaoMaster(db);

        daoSession = daoMaster.newSession();

        mFunctionTableDao = daoSession.getFunctionTableDao();
        contactInfoDao = daoSession.getContactInfoDao();
    }

    private void initMobileDatabase(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"mobile.db");

        SQLiteDatabase db = helper.getWritableDatabase();

        DaoMaster daoMaster = new DaoMaster(db);

        DaoSession daoSession = daoMaster.newSession();

        mobileDao = daoSession.getMobileDao();
    }

    public static MobileDao getMobileDao() {
        return mobileDao;
    }


    public static FunctionTableDao getFunctionTableDao(){
        return mFunctionTableDao;
    }
    public static ContactInfoDao getContactInfoDao(){
        return contactInfoDao;
    }



    //此初始化是一个耗时的操作，因此在要子线程中执行
    private void initFunctions(){
        //首先判断是否已经初始化，如果已经初始化，则不执行任何操作，如果没有初始化，则初始化
        SharedPreferences spf = BaiduUtils.getSharedPreferences();

        boolean isInitFunction = spf.getBoolean("init_function", false);
        if(!isInitFunction){
            //初始化
            String[] functionNames = getResources().getStringArray(R.array.function_name);
            String[] functionIcons = getResources().getStringArray(R.array.function_icon);

            for(int i = 0;i < functionNames.length;i++){
                FunctionTable table = new FunctionTable(null, functionNames[i], functionIcons[i], i, false);

                if(i == 0 || i == 1 || i == 2){
                    table.setFuncFixed(true);
                }


                getFunctionTableDao().insert(table);
            }

            spf.edit().putBoolean("init_function", true).commit();
        }
    }

    public static List<ContactInfo> getContactInfos() {
        return contactInfos;
    }
}


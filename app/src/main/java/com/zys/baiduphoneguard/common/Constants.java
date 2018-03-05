package com.zys.baiduphoneguard.common;

/**
 * Created by lenovo on 2017/4/18.
 */

public class Constants {
    //application中日志初始化的TAG
    public static final String APP_DEGUG_TAG = "BdApplication_Tag";
    //共享首选项的数据
    public static final String SHARED_DATA = "shared_data";
    //判断是都是第一次
    public static final String IS_FIRST_LAUNCH = "is_first_launch";
    //判断网络

    //请求码
    public static final int REQUEST_CODE = 0;

    //网络异常
    public static final int INTERNET_INTERRUPTED = 1;
    //数据访问失败
    public static final int INTERNET_FAILED = 2;
    //需要更新
    public static final int NEED_UPDATE = 3;
    //不需要更新
    public static final int NOT_NEED_UPDATE = 4;
    //网络为404
    public static final int  NOT_FOUND = 404;
    //
    public static final String CHECKUPATE_BROADCST = "checkUpdate_Broadcast";
    //服务器
    public static final String url = "http://zyscq.top:8080/";
}

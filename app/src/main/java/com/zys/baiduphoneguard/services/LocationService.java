package com.zys.baiduphoneguard.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zys.baiduphoneguard.BDApplication;
import com.zys.baiduphoneguard.dao.ContactInfoDao;
import com.zys.baiduphoneguard.entity.ContactInfo;
import com.zys.baiduphoneguard.utils.BaiduUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/11/13.
 */

public class LocationService extends Service {
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyBaiduLocation();
    //维度
    private Double latitude;
    //经度
    private Double longtitude;

    private String address;

    //因为定位需要一定的时间 ，所以我们需要等待一段时间再去发送消息
    //当经纬度 不为 null 设置定时器 1s后检查 得到位置信息后 取消定时器
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if(longtitude != null && latitude != null) {
                //紧急联系人在数据库中
                List<ContactInfo> contactInfos = BDApplication
                        .getContactInfoDao()
                        .queryBuilder()
                        .where(ContactInfoDao.Properties.IsUrgent.eq(true))
                        .build()
                        .list();
                for (int i = 0; i < contactInfos.size(); i++) {
                    ContactInfo contactInfo = contactInfos.get(i);

                    //求救短信（输入）  位置短信
                    SharedPreferences sp = BaiduUtils.getSharedPreferences();
                    String urgentContent = sp.getString("urgentContent", "赶紧来救我");
                    BaiduUtils.sendMessage(contactInfo.getPhone(), urgentContent);

                    String positionInfo = "http://map.baidu.com/?latlng=" + latitude + "," + longtitude + "&title=%E6%88%91%E7%9A%84%E4%BD%8D%E7%BD%AE&content=%E9%A9%AC%E4%B8%8A%E6%9D%A5%E6%95%91%E6%88%91&autoOpen=true&l";
                    BaiduUtils.sendMessage(contactInfo.getPhone(), positionInfo);

                    //停止定时器
                    timer.cancel();
                    mLocationClient.stop();
                    stopSelf();
                }
            }
        }
    };
    private Timer timer = new Timer();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);

        initLocation();
        mLocationClient.start();
        timer.scheduleAtFixedRate(task,500, 500);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化位置相关的参数
     */
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span=1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }




    public class MyBaiduLocation implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation != null){
                latitude = bdLocation.getLatitude();
                longtitude = bdLocation.getLongitude();
                address = bdLocation.getLocationDescribe();
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
}


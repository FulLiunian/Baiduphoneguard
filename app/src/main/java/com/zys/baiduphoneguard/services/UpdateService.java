package com.zys.baiduphoneguard.services;

import com.zys.baiduphoneguard.entity.UpdateInfo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/11/13.
 */

public interface UpdateService {
    @GET("UpdateServer/UpdateServlet")
    Call<UpdateInfo> getUpdateInfo();

}

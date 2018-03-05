package com.zys.baiduphoneguard.entity;

import java.io.Serializable;

public class UpdateInfo implements Serializable {

    /**
     * versionName : 1.0
     * versionCode : 2
     * apkUrl : http://zyscq.top:8080/UpdateServer/UpdateServlet
     * description : 有重大更新，马上行动吧！
     */

    private String versionName;
    private int versionCode;
    private String apkUrl;
    private String description;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

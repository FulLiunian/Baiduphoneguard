package com.zys.baiduphoneguard.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/11/13.
 */

@Entity(
        nameInDb = "dm_mobile",

        indexes = {
                @Index(value = "mobileNumber"),
                @Index(value = "areaCode")
        },

        createInDb = false
)
public class Mobile {
    @Property(nameInDb = "ID")
    private Integer id;
    @Property(nameInDb = "MobileNumber")
    private String mobileNumber;
    @Property(nameInDb = "MobileArea")
    private String mobileArea;
    @Property(nameInDb = "MobileType")
    private String mobileType;
    @Property(nameInDb = "AreaCode")
    private String areaCode;
    @Property(nameInDb = "PostCode")
    private String postCode;
@Generated(hash = 1296117221)
public Mobile(Integer id, String mobileNumber, String mobileArea,
        String mobileType, String areaCode, String postCode) {
    this.id = id;
    this.mobileNumber = mobileNumber;
    this.mobileArea = mobileArea;
    this.mobileType = mobileType;
    this.areaCode = areaCode;
    this.postCode = postCode;
}
@Generated(hash = 262536718)
public Mobile() {
}
public Integer getId() {
    return this.id;
}
public void setId(Integer id) {
    this.id = id;
}
public String getMobileNumber() {
    return this.mobileNumber;
}
public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
}
public String getMobileArea() {
    return this.mobileArea;
}
public void setMobileArea(String mobileArea) {
    this.mobileArea = mobileArea;
}
public String getMobileType() {
    return this.mobileType;
}
public void setMobileType(String mobileType) {
    this.mobileType = mobileType;
}
public String getAreaCode() {
    return this.areaCode;
}
public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
}
public String getPostCode() {
    return this.postCode;
}
public void setPostCode(String postCode) {
    this.postCode = postCode;
}
}

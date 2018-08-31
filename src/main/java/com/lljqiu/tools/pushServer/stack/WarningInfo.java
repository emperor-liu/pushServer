/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.stack
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.tools.pushServer.stack;

import java.io.Serializable;
import java.util.Date;

/** 
 * ClassName: WarningInfo.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: liujie@lljqiu.com <br>
 * Create Time: 2017年6月7日<br>
 */
public class WarningInfo implements Serializable {

    /**
     * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
     */
    private static final long serialVersionUID = 1L;

    private String            userId;
    private String            deviceId;
    private String            warnId;
    private String            deviceName;
    private String            devicePosition;
    private String            warnType;
    private String            picUrl;
    private String            videoUrl;
//    @JSONField(format="yyyy-MM-dd 24HH:mm:ss")
    private Date              warnTime;

    
    /**
     * @return the warnId
     */
    public String getWarnId() {
        return warnId;
    }

    /**
     * @param warnId the warnId to set
     */
    public void setWarnId(String warnId) {
        this.warnId = warnId;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName the deviceName to set
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * @return the devicePosition
     */
    public String getDevicePosition() {
        return devicePosition;
    }

    /**
     * @param devicePosition the devicePosition to set
     */
    public void setDevicePosition(String devicePosition) {
        this.devicePosition = devicePosition;
    }

    /**
     * @return the warnType
     */
    public String getWarnType() {
        return warnType;
    }

    /**
     * @param warnType the warnType to set
     */
    public void setWarnType(String warnType) {
        this.warnType = warnType;
    }

    /**
     * @return the picUrl
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * @param picUrl the picUrl to set
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    /**
     * @return the videoUrl
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * @param videoUrl the videoUrl to set
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * @return the warnTime
     */
    public Date getWarnTime() {
        return warnTime;
    }

    /**
     * @param warnTime the warnTime to set
     */
    public void setWarnTime(Date warnTime) {
        this.warnTime = warnTime;
    }

}

/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.huxiaosu.tools.pushserver.stack
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.tools.pushserver.stack;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/** 
 * ClassName: WarningInfo.java <br>
 * Description: <br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年6月7日<br>
 */
@Data
public class WarningInfo implements Serializable {

    /**
     *
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
    private Date              warnTime;

}

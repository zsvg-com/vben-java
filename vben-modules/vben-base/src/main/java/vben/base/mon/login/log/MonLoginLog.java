package vben.base.mon.login.log;

import lombok.Data;

import java.util.Date;

/**
 * 登录日志
 */
@Data
public class MonLoginLog {

    /**
     * 日志主键
     */
    private Long id;

    /**
     * 租户编号
     */
    private String tenid;

    /**
     * 用户账号
     */
    private String usnam;

    /**
     * 客户端
     */
    private String clkey;

    /**
     * 设备类型
     */
    private String detyp;

    /**
     * 登录状态 1成功 0失败
     */
    private Boolean sutag;

    /**
     * 登录IP地址
     */
    private String loip;

    /**
     * 登录地点
     */
    private String loloc;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;


    /**
     * 提示消息
     */
    private String himsg;


    /**
     * 登录时间
     */
    private Date lotim;

}

package vben.base.mon.online.user;

import lombok.Data;

/**
 * 当前在线会话
 *
 * @author Lion Li
 */
@Data
public class MonOnlineUser {

    /**
     * 会话编号
     */
    private String tokenId;

    /**
     * 部门名称
     */
    private String depna;

    /**
     * 用户名称
     */
    private String usena;

    /**
     * 客户端
     */
    private String clientKey;

    /**
     * 设备类型
     */
    private String detyp;

    /**
     * 登录IP地址
     */
    private String ip;

    /**
     * 登录地址
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录时间
     */
    private Long loginTime;

}

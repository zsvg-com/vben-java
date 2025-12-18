package vben.base.mon.oper.log;

import lombok.Data;

import java.util.Date;

/**
 * 操作日志
 */
@Data
public class MonOperLog {

    /**
     * 日志主键
     */
    private Long id;

    /**
     * 租户编号
     */
    private String tenid;

    /**
     * 操作模块
     */
    private String opmod;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private Integer butyp;

    /**
     * 请求方法
     */
    private String remet;

    /**
     * 请求方式
     */
    private String reway;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    private Integer optyp;

    /**
     * 操作人员
     */
    private String opuna;

    /**
     * 部门名称
     */
    private String opdna;

    /**
     * 请求url
     */
    private String reurl;

    /**
     * 操作地址
     */
    private String opip;

    /**
     * 操作地点
     */
    private String oploc;

    /**
     * 请求参数
     */
    private String repar;

    /**
     * 返回参数
     */
    private String bapar;

    /**
     * 操作状态（1成功 0失败）
     */
    private Boolean sutag;

    /**
     * 错误消息
     */
    private String ermsg;

    /**
     * 操作时间
     */
    private Date optim;

    /**
     * 消耗时间
     */
    private Long cotim;

}

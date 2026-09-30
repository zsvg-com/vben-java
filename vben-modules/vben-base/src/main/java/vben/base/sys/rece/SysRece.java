package vben.base.sys.rece;

import lombok.Data;

import java.util.Date;

/**
 * 系统参与者最近访问记录
 */
@Data
public class SysRece {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String useid;

    /**
     * 最近使用的系统参与者ID
     */
    private String aid;

    /**
     * 最近使用时间
     */
    private Date uptim = new Date();
}

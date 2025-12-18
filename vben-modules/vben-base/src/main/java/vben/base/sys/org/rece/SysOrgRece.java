package vben.base.sys.org.rece;

import lombok.Data;

import java.util.Date;

/**
 * 组织架构最近访问记录
 */
@Data
public class SysOrgRece {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String useid;

    /**
     * 最近使用的组织架构ID
     */
    private String oid;

    /**
     * 最近使用时间
     */
    private Date uptim = new Date();
}

package vben.base.tool.oss.main;

import lombok.Data;

import java.util.Date;

/**
 * OSS存储引用
 */
@Data
public class ToolOssMain {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 类型（后缀）
     */
    private String type;

    /**
     * 文件ID
     */
    private String filid;

    /**
     * 业务ID
     */
    private String busid;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 创建人ID
     */
    private String cruid;

}

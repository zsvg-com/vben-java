package vben.base.tool.oss.file;

import lombok.Data;

import java.util.Date;

@Data
public class ToolOssFile {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 文件md5
     */
    private String md5;

    /**
     * 文件大小
     */
    private Long fsize;

    /**
     * 存储地址
     */
    private String path;

    /**
     * 存储服务
     */
    private String service;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

}

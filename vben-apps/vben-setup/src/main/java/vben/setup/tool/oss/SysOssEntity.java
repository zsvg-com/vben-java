package vben.setup.tool.oss;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

/**
 * OSS对象存储对象
 *
 * @author Lion Li
 */
@Data
@Entity
@Table(name = "sys_oss")
public class SysOssEntity {

    /**
     * 搜索值
     */
    @JsonIgnore
    private String searchValue;

    /**
     * 创建部门
     */
    private Long createDept;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 请求参数
     */
//    private Map<String, Object> params = new HashMap<>();

    private String tenantId;
    /**
     * 对象存储主键
     */
    @Id
    private Long ossId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 原名
     */
    private String originalName;

    /**
     * 文件后缀名
     */
    private String fileSuffix;

    /**
     * URL地址
     */
    private String url;

    /**
     * 扩展字段
     */
    private String ext1;

    /**
     * 服务商
     */
    private String service;

}

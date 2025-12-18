package vben.common.mybatis.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * CateEntity基类
 *
 * @author baby_fox
 */
@Data
public class BaseCateEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    @TableField(fill = FieldFill.INSERT)
    private Long id;

    /**
     * 名称
     */
    @TableField(fill = FieldFill.INSERT)
    private String name;

    /**
     * 父ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long pid;

    /**
     * 层级
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String tier;

    /**
     * 子分类
     */
    @TableField(exist = false)
    private List<BaseCateEntity> children = new ArrayList<>();

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 备注
     */
    @TableField(fill = FieldFill.INSERT)
    private String notes;

    /**
     * 可用标记
     */
    @TableField(fill = FieldFill.INSERT)
    private Boolean avtag;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date crtim;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private String cruid;

//    /**
//     * 创建人
//     */
//    @TableField(exist = false)
//    private SysOrg crman;

    /**
     * 创建人姓名
     */
    @TableField(exist = false)
    private String cruna;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date uptim;


    /**
     * 更新人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String upuid;

//    /**
//     * 更新人
//     */
//    @TableField(exist = false)
//    private SysOrg upman;

    /**
     * 更新人姓名
     */
    @TableField(exist = false)
    private String upuna;

//
//    /**
//     * 标签
//     */
//    private String label;


    /**
     * 搜索值
     */
    @JsonIgnore
    @TableField(exist = false)
    private String searchValue;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

//    /**
//     * 创建部门
//     */
//    @TableField(fill = FieldFill.INSERT)
//    private String createDept;


}

package vben.common.jpa.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//分类Entity基类，可实现无限多级的树结构
@MappedSuperclass
@Getter
@Setter
public class BaseStrCateEntity {
    @Id
    @Column(length = 36)
    @Schema(description = "主键ID")
    protected String id;

    //----------------------层级属性参考-----------------------
    @Column(length = 512)
    @Schema(description = "层级信息")
    protected String tier;

    @Column(length = 36)
    @Schema(description = "父分类ID")
    protected String pid;

//    @Transient
//    protected BaseStrCateEntity parent;
//
    @Transient
    protected List<BaseStrCateEntity> children = new ArrayList<>();
    //----------------------通用属性-----------------------
    @Schema(description = "名称")
    protected String name;

    @Column(length = 64)
    @Schema(description = "标签")
    protected String label;

    @Column(length = 500)
    @Schema(description = "备注")
    protected String notes;

    @Schema(description = "可用标记 1启用，0禁用")
    protected Boolean avtag;

    @Schema(description = "排序号")
    protected Integer ornum;

    @ManyToOne
    @JoinColumn(name = "cruid", updatable = false)
    @Schema(description = "创建人")
    protected SysOrg crman;

    @Transient
    protected String cruna;

    @Column(updatable = false)
    @Schema(description = "创建时间")
    protected Date crtim = new Date();

    @Schema(description = "更新时间")
    protected Date uptim;

    @ManyToOne
    @JoinColumn(name = "upuid")
    @Schema(description = "更新人")
    protected SysOrg upman;

    @Transient
    protected String upuna;

}

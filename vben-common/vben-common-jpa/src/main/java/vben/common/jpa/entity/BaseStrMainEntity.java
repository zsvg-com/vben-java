package vben.common.jpa.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//主数据Entity基类，提供通用的字段
@MappedSuperclass
@Getter
@Setter
public class BaseStrMainEntity {
    //----------------------id属性-----------------------
    @Id
    @Column(length = 32)
    @Schema(description = "主键ID")
    protected String id;

    //----------------------权限属性-----------------------
//    @ManyToMany
//    @JoinTable(name = "xxx_viewer", joinColumns = {@JoinColumn(name = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "org")})
//    private List<SysOrg> viewers;//记录可查看者
//
//    @ManyToMany
//    @JoinTable(name = "xxx_editor", joinColumns = {@JoinColumn(name = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "org")})
//    private List<SysOrg> editors;//记录可编辑者


    //----------------------norm标准属性-----------------------
    @Column(length = 126)
    @Schema(description = "名称")
    protected String name;

    @Schema(description = "可用标记")
    protected Boolean avtag = true;// 1启用，0禁用

    @ManyToOne
    @JoinColumn(name = "cruid", updatable = false)
    @Schema(description = "创建人ID")
    protected SysOrg crman;

    @Column(updatable = false)
    @Schema(description = "创建时间")
    protected Date crtim = new Date();

    @ManyToOne
    @JoinColumn(name = "upuid")
    @Schema(description = "更新人")
    protected SysOrg upman;

    @Schema(description = "更新时间")
    protected Date uptim;

    @Schema(description = "备注")
    protected String notes;

}

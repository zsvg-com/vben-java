package vben.setup.demo.link;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vben.common.jpa.entity.BaseMainEntity;
import vben.common.jpa.entity.SysOrg;

import java.util.ArrayList;
import java.util.List;

/**
 * 关联主表
 */
@Entity
@Getter
@Setter
@Schema(description = "关联主表")
@Table(indexes = {@Index(columnList = "cruid"), @Index(columnList = "upuid")})
public class DemoLinkMain extends BaseMainEntity {

    /**
     * 所属分类ID
     */
    @Schema(description = "所属分类ID")
    private Long catid;

    /**
     * 子项目列表（OneToMany示例）
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "maiid")
    @OrderBy("ornum ASC")
    @Schema(description = "子项目列表")
    private List<DemoLinkItem> items = new ArrayList<>();

    /**
     * 成员列表（ManyToMany示例）
     */
    @ManyToMany
    @JoinTable(name = "demo_link_main_org", joinColumns = {@JoinColumn(name = "mid")},
        inverseJoinColumns = {@JoinColumn(name = "oid")})
    @Schema(description = "成员信息")
    private List<SysOrg> orgs;

}

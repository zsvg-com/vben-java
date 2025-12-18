package vben.setup.bpm.org;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "bpm_org_node")
public class BpmOrgNodeEntity {

    @Id
    @Column(length = 32)
    private String id;//ID

    @Column(length = 100)
    private String name;//名称

    @Column(length = 32)
    private String pid;//父ID

    private String treid;//角色树id

    @Column(length = 1000)
    private String tier;//层级

    private String notes;//备注

    private Integer ornum;//排序号

    @Column(updatable = false)
    private Date crtim = new Date();//创建时间

    private Date uptim;//更新时间

    private Boolean avtag;//可用标记

    /**
     * 成员ID
     */
    @Column(length = 36)
    @Schema(description = "成员ID")
    private String memid;
}

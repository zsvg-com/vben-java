package vben.setup.bpm.org;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "bpm_org_tree")
public class BpmOrgTreeEntity {

    /**
     * 主键
     */
    @Id
    @Column(length = 32)
    private String id;

    /**
     * 名称
     */
    @Column(length = 32)
    private String name;

    /**
     * 分类ID
     */
    @Column(length = 32)
    private String catid;

    /**
     * 可用标记
     */
    private Boolean avtag = true;

    /**
     * 创建人
     */
    @Column(length = 36)
    private String cruid;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新人
     */
    @Column(length = 36)
    private String upuid;

    /**
     * 更新时间
     */
    private Date uptim;

    /**
     * 备注
     */
    private String notes;

    /**
     * 排序号
     */
    private Integer ornum;

}

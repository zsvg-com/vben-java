package vben.setup.bpm.org;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


/**
 * 群组分类
 */
@Data
@Entity
@Table(name = "bpm_org_tree_cate")
public class BpmOrgTreeCateEntity {
    /**
     * 主键ID
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
     * 父ID
     */
    @Column(length = 32)
    private String pid;

    /**
     * 排序号
     */
    private Integer ornum;

}

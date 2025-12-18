package vben.setup.bpm.bus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "bpm_bus_cate")
public class BpmBusCateEntity {

    /**
     * 主键ID
     */
    @Id
    private Long id;

    /**
     * 名称
     */
    @Column(length = 64)
    private String name;

    /**
     * 父ID
     */
    private Long pid;


    /**
     * 是否可用
     */
    private Boolean avtag;

    /**
     * 排序号
     */
    private Integer ornum;


    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新时间
     */
    private Date uptim;

    /**
     * 创建人ID
     */
    @Column(length = 36)
    private String cruid;

    /**
     * 更新人ID
     */
    @Column(length = 36)
    private String upuid;

    private String notes;

}

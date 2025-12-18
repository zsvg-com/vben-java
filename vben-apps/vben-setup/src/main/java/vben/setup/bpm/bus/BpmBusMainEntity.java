package vben.setup.bpm.bus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "bpm_bus_main")
public class BpmBusMainEntity {

    @Id
    @Column(length = 32)
    @Schema(description = "主键ID")
    private Long id;


    private String name;

    private Boolean avtag = true;

    @Column(length = 36)
    private String cruid;

    private Date crtim = new Date();

    @Column(length = 36)
    private String upuid;

    private Date uptim;

    private String notes;

    /**
     * 流程模板ID
     */
    private Long tmpid;

    /**
     * 流程状态
     */
    @Column(length = 8)
    private String state;

    /**
     * 流程定义ID
     */
    private Long prdid;

    /**
     * 表单定义ID
     */
    private Long fodid;

    /**
     * 表单数据
     */
    @Column(length = 4000)
    private String fdata;

}

package vben.setup.mon;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "mon_job_main")
public class MonJobMainEntity
{
    /**
     *主键ID
     */
    @Id
    private Long id;

    /**
     *任务名称
     */
    @Column(length = 128, updatable = false)
    private String name;

    /**
     *任务代码
     */
    @Column(length = 128, updatable = false)
    private String code;

//    @Column(length = 32, updatable = false)
    @Transient
    private String jid;
//
//    @Column(length = 32, updatable = false)
    @Transient
    private String jgroup;

    /**
     *请求类型
     */
    private Integer retyp;

    /**
     *请求URL
     */
    @Column(length = 32, updatable = false)
    private String reurl;

    /**
     *可用标记
     */
    private Boolean avtag;

    /**
     *排序号
     */
    private Integer ornum;

    /**
     *创建时间
     */
    @Column(updatable = false)
    private Date crtim = new Date();

    /**
     *执行表达式
     */
    @Column(length = 32)
    private String cron;

    /**
     *备注
     */
    private String notes;

}

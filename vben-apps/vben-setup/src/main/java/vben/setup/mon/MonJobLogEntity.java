package vben.setup.mon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "mon_job_log")
public class MonJobLogEntity {

    /**
     *主键ID
     */
    @Id
    private Long id;

    /**
     *任务名称
     */
    @Column(length = 32)
    private String name;

    /**
     *开始时间
     */
    @Column(length = 32)
    private String sttim;

    /**
     *结束时间
     */
    @Column(length = 32)
    private String entim;

    /**
     *运行结果
     */
    @Column(length = 32)
    private String ret;

    /**
     *日志信息
     */
    @Column(length = 5000)
    private String msg;
}

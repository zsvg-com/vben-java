package vben.bpm.proc.inst;

import lombok.Data;
import vben.bpm.root.domain.Zproc;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class BpmProcInst implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 主题
     */
    private String name;

    /**
     * 可用标记
     */
    private Boolean avtag = true;

    /**
     * 创建人
     */
    private String cruid;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新人
     */
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
     * 业务ID
     */
    private String busid;

    /**
     * 业务类型
     */
    private String busty;


    /**
     * 流程定义ID
     */
    private Long prdid;

    /**
     * 状态
     */
    private String state;

    public BpmProcInst() {

    }

    public BpmProcInst(Zproc zproc) {
        this.id= zproc.getProid();
        this.name= zproc.getProna();
        this.prdid= zproc.getPrdid();
        this.cruid= zproc.getInuid();
    }

}

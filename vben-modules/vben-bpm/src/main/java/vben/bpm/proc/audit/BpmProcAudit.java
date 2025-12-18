package vben.bpm.proc.audit;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class BpmProcAudit implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 当前节点ID:N1,N2
     */
    private String facno;

    /**
     * 当前节点名称
     */
    private String facna;

    /**
     * 开始时间
     */
    protected Date crtim = new Date();

    /**
     * 流程id
     */
    private Long proid;

    /**
     * 节点id
     */
    private Long nodid;

    /**
     * 任务id
     */
    private Long tasid;

    /**
     * 实处理人ID
     */
    private String hauid;



//    private String auuid;//授权处理人

//    private String exuid;//应处理人

    /**
     * 操作的key：pass，refuse
     */
    private String opkey;

    /**
     * 操作的名称: 通过，驳回，转办等
     */
    private String opinf;

    /**
     * 审核留言：具体写了什么审核内容
     */
    private String opnot;

    /**
     * 审核附件IDS
     */
    private String atids;

}

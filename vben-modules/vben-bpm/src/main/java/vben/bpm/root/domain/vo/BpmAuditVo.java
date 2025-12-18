package vben.bpm.root.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class BpmAuditVo implements Serializable {

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
    private Date crtim;

    /**
     * 实处理人姓名
     */
    private String hauna;


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

    /**
     * 历史处理人
     */
    private String hiHamen;

}

package vben.bpm.proc.node;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class BpmProcNodeHi implements Serializable {

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
     * 当前节点类型
     */
    private String facty;

    /**
     * 流转方式
     */
    private String flway;

    /**
     * 目标节点ID
     */
    private String tarno;

    /**
     * 目标节点名称
     */
    private String tarna;

    /**
     * 流程实例id
     */
    private Long proid;

    /**
     * 状态
     */
    private String state;

    /**
     * 开始时间
     */
    private Date sttim = new Date();

    /**
     * 结束时间
     */
    private Date entim;

}

package vben.bpm.proc.node;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class BpmProcNode implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 当前节点编号:N1,N2
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
    private String flway;//1 顺签 2 或签 3 会签

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

}

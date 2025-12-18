package vben.bpm.root.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BpmTaskVo implements Serializable {

    /**
     * 当前任务ID
     */
    private Long tasid;

    /**
     * 当前任务类型
     */
    private String tasty;

    /**
     * 当前节点ID
     */
    private Long nodid;

    /**
     * 当前节点名称
     */
    private String nodna;

    /**
     * 当前节点编号
     */
    private String nodno;


    /**
     * 当前节点处理人
     */
    private String exuna;

    private boolean crtag=false;

    /**
     * 其他处理人ID
     */
    private String exuid;

}
